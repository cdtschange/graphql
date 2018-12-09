package com.cdts.graphql.controller

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import graphql.schema.GraphQLObjectType
import graphql.schema.TypeResolver
import graphql.schema.idl.EnumValuesProvider


public class StarWarsData {
    companion object {
        var field = "no field"
        val luke = mapOf(
                "id" to "1000",
                "name" to "Luke Skywalker",
                "friends" to listOf("1002", "1003", "2000", "2001"),
                "appearsIn" to listOf(4, 5, 6),
                "homePlanet" to "Tatooine"
        )
        val vader = mapOf(
                "id" to "1001",
                "name" to "Darth Vader",
                "friends" to listOf("1004"),
                "appearsIn" to listOf(4, 5, 6),
                "homePlanet" to "Tatooine"
        )
        val han = mapOf(
                "id" to "1002",
                "name" to "Han Solo",
                "friends" to listOf("1000", "1003", "2001"),
                "appearsIn" to listOf(4, 5, 6)
        )
        val leia = mapOf(
                "id" to "1003",
                "name" to "Leia Organa",
                "friends" to listOf("1000", "1002", "2000", "2001"),
                "appearsIn" to listOf(4, 5, 6),
                "homePlanet" to "Alderaan"
        )
        val tarkin = mapOf(
                "id" to "1004",
                "name" to "Wilhuff Tarkin",
                "friends" to listOf("1001"),
                "appearsIn" to listOf(4)
        )

        val humanData = mapOf("1000" to luke, "1001" to vader, "1002" to han, "1003" to leia, "1004" to tarkin)

        val threepio = mapOf(
                "id" to "2000",
                "name" to "C-3PO",
                "friends" to listOf("1000", "1002", "1003", "2001"),
                "appearsIn" to listOf(4, 5, 6),
                "primaryFunction" to "Protocol"
        )
        val artoo = mapOf(
                "id" to "2001",
                "name" to "R2-D2",
                "friends" to listOf("1000", "1002", "1003"),
                "appearsIn" to listOf(4, 5, 6),
                "primaryFunction" to "Astromech"
        )

        val droidData = mapOf("2000" to threepio, "2001" to artoo)

        fun isHuman(id: String): Boolean {
            return humanData[id] != null
        }

        fun getCharacter(id: String): Map<String, Any>? {
            if (humanData[id] != null) return humanData[id]
            if (droidData[id] != null) return droidData[id]
            return null
        }

        val humanDataFetcher = DataFetcher { environment ->
            val id = environment.arguments["id"]
            humanData[id]
        }
        val droidDataFetcher = DataFetcher { environment ->
            val id = environment.arguments["id"]
            droidData[id]
        }
        val characterTypeResolver: TypeResolver = TypeResolver { env ->
            val id = env.getObject<Map<String, Any>>()["id"]
            when {
//                humanData[id] != null -> StarWarsSchema.humanType
//                droidData[id] != null -> StarWarsSchema.droidType
                humanData[id] != null -> env.schema.getType("Human") as GraphQLObjectType
                droidData[id] != null -> env.schema.getType("Droid") as GraphQLObjectType
                else -> null
            }
        }
        val friendsDataFetcher: DataFetcher<Any> = DataFetcher { environment ->
            var result = mutableListOf<Any?>()
            environment.getSource<Map<String, List<String>>>()["friends"]?.let { list ->
                for (id in list) {
                    result.add(getCharacter(id))
                }
            }
            result
        }
        val heroDataFetcher: DataFetcher<Any> = DataFetcher { environment ->
            if (environment.containsArgument("episode")
                    && 5 == environment.getArgument("episode")) {
                luke
            } else {
                artoo
            }
        }
        val episodeResolver: EnumValuesProvider = EnumValuesProvider { name ->
            when (name) {
                "NEWHOPE" -> 4
                "EMPIRE" -> 5
                "JEDI" -> 6
                else -> null
            }
        }

        val fieldFetcher: DataFetcher<String> = DataFetcher { environment ->
            field
        }
    }
}