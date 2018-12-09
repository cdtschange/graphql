package com.cdts.graphql.controller

import com.google.common.io.CharStreams
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import graphql.ExecutionInput
import graphql.GraphQL
import graphql.introspection.IntrospectionQuery
import graphql.schema.GraphQLSchema
import graphql.schema.StaticDataFetcher
import graphql.schema.TypeResolver
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.RuntimeWiring.newRuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList
import graphql.schema.GraphQLObjectType
import org.springframework.web.bind.annotation.RequestBody


@Controller
@RequestMapping("/home")
class HomeController {

    @RequestMapping("/index")
    @ResponseBody
    fun index(): String {
        val schema = "type Query{hello: String}"

        val schemaParser = SchemaParser()
        val typeDefinitionRegistry = schemaParser.parse(schema)

        val runtimeWiring = newRuntimeWiring()
                .type("Query") { builder -> builder.dataFetcher("hello", StaticDataFetcher("world")) }
                .build()

        val schemaGenerator = SchemaGenerator()
        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring)

        val build = GraphQL.newGraphQL(graphQLSchema).build()
        val executionResult = build.execute("{hello}")
        // Prints: {hello=world}

        return executionResult.getData<Any>().toString()
    }

    @RequestMapping("/query")
    @ResponseBody
    fun query(@RequestParam(required = false) ghql: String): String {
        var ghql = ghql
        if (StringUtils.isEmpty(ghql)) {
            ghql = "query { hero { name } }"
        }

        var build: GraphQL? = null
        try {
            build = GraphQL.newGraphQL(graphQLSchema()).build()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val executionResult = build!!.execute(ghql)
        // Prints: {hello=world}

        return Gson().toJson(executionResult.getData<Any>())
    }

    @RequestMapping("/api")
    @ResponseBody
    fun api(@RequestBody body: String): String {
        val turnsType = object : TypeToken<Map<String, Any>>() {}.type
        var map: Map<String, Any> = Gson().fromJson(body, turnsType)
        var query = map["query"]?.toString()
        var params = map["variables"] as? Map<String, Any>

        var build: GraphQL? = null
        try {
            build = GraphQL.newGraphQL(graphQLSchema()).build()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var input = ExecutionInput.newExecutionInput().query(query)
        if (params != null) {
            input = input.variables(params!!)
        }

        val executionResult = build!!.execute(input.build())
        // Prints: {hello=world}

        var result = mutableMapOf<String, Any>()
        result["data"] = executionResult.getData<Any>()

        return Gson().toJson(result)
    }
    @RequestMapping("/graphql")
    @ResponseBody
    fun graphql(): String {
        var ghql = IntrospectionQuery.INTROSPECTION_QUERY

        var build: GraphQL? = null
        try {
            build = GraphQL.newGraphQL(graphQLSchema()).build()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val executionResult = build!!.execute(ghql)
        // Prints: {hello=world}

        return Gson().toJson(executionResult.getData<Any>())
    }


    @Throws(IOException::class)
    fun graphQLSchema(): GraphQLSchema {
        val schemaParser = SchemaParser()
        val schemaGenerator = SchemaGenerator()
        val schemaFileContent = readSchemaFileContent()
        val typeRegistry = schemaParser.parse(schemaFileContent)
        val wiring = buildRuntimeWiring()

        return schemaGenerator.makeExecutableSchema(typeRegistry, wiring)
    }

    @Throws(IOException::class)
    private fun readSchemaFileContent(): String {
        val classPathResource = ClassPathResource("myschema.graphqls")
        classPathResource.inputStream.use { inputStream -> return CharStreams.toString(InputStreamReader(inputStream, Charsets.UTF_8)) }
    }


    private fun buildRuntimeWiring(): RuntimeWiring {

        return RuntimeWiring.newRuntimeWiring()
                // this uses builder function lambda syntax
                .type("QueryType") { typeWiring ->
                    typeWiring
                            .dataFetcher("hero", StaticDataFetcher (StarWarsData.artoo))
                            .dataFetcher("human", StarWarsData.humanDataFetcher)
                            .dataFetcher("droid", StarWarsData.droidDataFetcher)
                            .dataFetcher("field", StarWarsData.fieldFetcher)
                }
                .type("Human") { typeWiring ->
                    typeWiring
                            .dataFetcher("friends", StarWarsData.friendsDataFetcher)
                }
                // you can use builder syntax if you don't like the lambda syntax
                .type("Droid") { typeWiring ->
                    typeWiring
                            .dataFetcher("friends", StarWarsData.friendsDataFetcher)
                }
                // or full builder syntax if that takes your fancy
                .type(
                        newTypeWiring("Character")
                                .typeResolver(StarWarsData.characterTypeResolver)
                                .build()
                )
                .type(
                        newTypeWiring("Episode")
                                .enumValues(StarWarsData.episodeResolver)
                                .build()
                )
                .build()
    }
}