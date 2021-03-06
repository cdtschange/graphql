package com.cdts.graphql.controller

import graphql.schema.GraphQLEnumType
import graphql.schema.GraphQLInterfaceType
import graphql.schema.GraphQLObjectType
import graphql.schema.GraphQLSchema
import graphql.schema.StaticDataFetcher

import graphql.Scalars.GraphQLString
import graphql.schema.GraphQLArgument.newArgument
import graphql.schema.GraphQLEnumType.newEnum
import graphql.schema.GraphQLFieldDefinition.newFieldDefinition
import graphql.schema.GraphQLInterfaceType.newInterface
import graphql.schema.GraphQLList.list
import graphql.schema.GraphQLNonNull.nonNull
import graphql.schema.GraphQLObjectType.newObject
import graphql.schema.GraphQLTypeReference.typeRef

object StarWarsSchema {


    var episodeEnum = newEnum()
            .name("Episode")
            .description("One of the films in the Star Wars Trilogy")
            .value("NEWHOPE", 4, "Released in 1977.")
            .value("EMPIRE", 5, "Released in 1980.")
            .value("JEDI", 6, "Released in 1983.")
            .build()


    var characterInterface = newInterface()
            .name("Character")
            .description("A character in the Star Wars Trilogy")
            .field(newFieldDefinition()
                    .name("id")
                    .description("The id of the character.")
                    .type(nonNull(GraphQLString)))
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the character.")
                    .type(GraphQLString))
            .field(newFieldDefinition()
                    .name("friends")
                    .description("The friends of the character, or an empty list if they have none.")
                    .type(list(typeRef("Character"))))
            .field(newFieldDefinition()
                    .name("appearsIn")
                    .description("Which movies they appear in.")
                    .type(list(episodeEnum)))
            .typeResolver(StarWarsData.characterTypeResolver)
            .build()

    var humanType = newObject()
            .name("Human")
            .description("A humanoid creature in the Star Wars universe.")
            .withInterface(characterInterface)
            .field(newFieldDefinition()
                    .name("id")
                    .description("The id of the human.")
                    .type(nonNull(GraphQLString)))
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the human.")
                    .type(GraphQLString))
            .field(newFieldDefinition()
                    .name("friends")
                    .description("The friends of the human, or an empty list if they have none.")
                    .type(list(characterInterface))
                    .dataFetcher(StarWarsData.friendsDataFetcher))
            .field(newFieldDefinition()
                    .name("appearsIn")
                    .description("Which movies they appear in.")
                    .type(list(episodeEnum)))
            .field(newFieldDefinition()
                    .name("homePlanet")
                    .description("The home planet of the human, or null if unknown.")
                    .type(GraphQLString))
            .build()

    var droidType = newObject()
            .name("Droid")
            .description("A mechanical creature in the Star Wars universe.")
            .withInterface(characterInterface)
            .field(newFieldDefinition()
                    .name("id")
                    .description("The id of the droid.")
                    .type(nonNull(GraphQLString)))
            .field(newFieldDefinition()
                    .name("name")
                    .description("The name of the droid.")
                    .type(GraphQLString))
            .field(newFieldDefinition()
                    .name("friends")
                    .description("The friends of the droid, or an empty list if they have none.")
                    .type(list(characterInterface))
                    .dataFetcher(StarWarsData.friendsDataFetcher))
            .field(newFieldDefinition()
                    .name("appearsIn")
                    .description("Which movies they appear in.")
                    .type(list(episodeEnum)))
            .field(newFieldDefinition()
                    .name("primaryFunction")
                    .description("The primary function of the droid.")
                    .type(GraphQLString))
            .build()


    var queryType = newObject()
            .name("QueryType")
            .field(newFieldDefinition()
                    .name("hero")
                    .type(characterInterface)
                    .argument(newArgument()
                            .name("episode")
                            .description("If omitted, returns the hero of the whole saga. If provided, returns the hero of that particular episode.")
                            .type(episodeEnum))
                    .dataFetcher(StaticDataFetcher(StarWarsData.artoo)))
            .field(newFieldDefinition()
                    .name("human")
                    .type(humanType)
                    .argument(newArgument()
                            .name("id")
                            .description("id of the human")
                            .type(nonNull(GraphQLString)))
                    .dataFetcher(StarWarsData.humanDataFetcher))
            .field(newFieldDefinition()
                    .name("droid")
                    .type(droidType)
                    .argument(newArgument()
                            .name("id")
                            .description("id of the droid")
                            .type(nonNull(GraphQLString)))
                    .dataFetcher(StarWarsData.droidDataFetcher))
            .build()


    var starWarsSchema = GraphQLSchema.newSchema()
            .query(queryType)
            .build()
}