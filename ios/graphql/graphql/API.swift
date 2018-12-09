//  This file was automatically generated and should not be edited.

import Apollo

public enum Episode: RawRepresentable, Equatable, Hashable, Apollo.JSONDecodable, Apollo.JSONEncodable {
  public typealias RawValue = String
  case newhope
  case empire
  case jedi
  /// Auto generated constant for unknown enum values
  case __unknown(RawValue)

  public init?(rawValue: RawValue) {
    switch rawValue {
      case "NEWHOPE": self = .newhope
      case "EMPIRE": self = .empire
      case "JEDI": self = .jedi
      default: self = .__unknown(rawValue)
    }
  }

  public var rawValue: RawValue {
    switch self {
      case .newhope: return "NEWHOPE"
      case .empire: return "EMPIRE"
      case .jedi: return "JEDI"
      case .__unknown(let value): return value
    }
  }

  public static func == (lhs: Episode, rhs: Episode) -> Bool {
    switch (lhs, rhs) {
      case (.newhope, .newhope): return true
      case (.empire, .empire): return true
      case (.jedi, .jedi): return true
      case (.__unknown(let lhsValue), .__unknown(let rhsValue)): return lhsValue == rhsValue
      default: return false
    }
  }
}

public final class HeroNameQueryQuery: GraphQLQuery {
  public let operationDefinition =
    "query HeroNameQuery {\n  hero {\n    __typename\n    name\n  }\n}"

  public init() {
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["QueryType"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("hero", type: .object(Hero.selections)),
    ]

    public private(set) var resultMap: ResultMap

    public init(unsafeResultMap: ResultMap) {
      self.resultMap = unsafeResultMap
    }

    public init(hero: Hero? = nil) {
      self.init(unsafeResultMap: ["__typename": "QueryType", "hero": hero.flatMap { (value: Hero) -> ResultMap in value.resultMap }])
    }

    public var hero: Hero? {
      get {
        return (resultMap["hero"] as? ResultMap).flatMap { Hero(unsafeResultMap: $0) }
      }
      set {
        resultMap.updateValue(newValue?.resultMap, forKey: "hero")
      }
    }

    public struct Hero: GraphQLSelectionSet {
      public static let possibleTypes = ["Human", "Droid"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("name", type: .nonNull(.scalar(String.self))),
      ]

      public private(set) var resultMap: ResultMap

      public init(unsafeResultMap: ResultMap) {
        self.resultMap = unsafeResultMap
      }

      public static func makeHuman(name: String) -> Hero {
        return Hero(unsafeResultMap: ["__typename": "Human", "name": name])
      }

      public static func makeDroid(name: String) -> Hero {
        return Hero(unsafeResultMap: ["__typename": "Droid", "name": name])
      }

      public var __typename: String {
        get {
          return resultMap["__typename"]! as! String
        }
        set {
          resultMap.updateValue(newValue, forKey: "__typename")
        }
      }

      public var name: String {
        get {
          return resultMap["name"]! as! String
        }
        set {
          resultMap.updateValue(newValue, forKey: "name")
        }
      }
    }
  }
}

public final class NestedQueryQuery: GraphQLQuery {
  public let operationDefinition =
    "query NestedQuery {\n  hero {\n    __typename\n    name\n    friends {\n      __typename\n      name\n      appearsIn\n      friends {\n        __typename\n        name\n      }\n    }\n  }\n}"

  public init() {
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["QueryType"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("hero", type: .object(Hero.selections)),
    ]

    public private(set) var resultMap: ResultMap

    public init(unsafeResultMap: ResultMap) {
      self.resultMap = unsafeResultMap
    }

    public init(hero: Hero? = nil) {
      self.init(unsafeResultMap: ["__typename": "QueryType", "hero": hero.flatMap { (value: Hero) -> ResultMap in value.resultMap }])
    }

    public var hero: Hero? {
      get {
        return (resultMap["hero"] as? ResultMap).flatMap { Hero(unsafeResultMap: $0) }
      }
      set {
        resultMap.updateValue(newValue?.resultMap, forKey: "hero")
      }
    }

    public struct Hero: GraphQLSelectionSet {
      public static let possibleTypes = ["Human", "Droid"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("name", type: .nonNull(.scalar(String.self))),
        GraphQLField("friends", type: .list(.object(Friend.selections))),
      ]

      public private(set) var resultMap: ResultMap

      public init(unsafeResultMap: ResultMap) {
        self.resultMap = unsafeResultMap
      }

      public static func makeHuman(name: String, friends: [Friend?]? = nil) -> Hero {
        return Hero(unsafeResultMap: ["__typename": "Human", "name": name, "friends": friends.flatMap { (value: [Friend?]) -> [ResultMap?] in value.map { (value: Friend?) -> ResultMap? in value.flatMap { (value: Friend) -> ResultMap in value.resultMap } } }])
      }

      public static func makeDroid(name: String, friends: [Friend?]? = nil) -> Hero {
        return Hero(unsafeResultMap: ["__typename": "Droid", "name": name, "friends": friends.flatMap { (value: [Friend?]) -> [ResultMap?] in value.map { (value: Friend?) -> ResultMap? in value.flatMap { (value: Friend) -> ResultMap in value.resultMap } } }])
      }

      public var __typename: String {
        get {
          return resultMap["__typename"]! as! String
        }
        set {
          resultMap.updateValue(newValue, forKey: "__typename")
        }
      }

      public var name: String {
        get {
          return resultMap["name"]! as! String
        }
        set {
          resultMap.updateValue(newValue, forKey: "name")
        }
      }

      public var friends: [Friend?]? {
        get {
          return (resultMap["friends"] as? [ResultMap?]).flatMap { (value: [ResultMap?]) -> [Friend?] in value.map { (value: ResultMap?) -> Friend? in value.flatMap { (value: ResultMap) -> Friend in Friend(unsafeResultMap: value) } } }
        }
        set {
          resultMap.updateValue(newValue.flatMap { (value: [Friend?]) -> [ResultMap?] in value.map { (value: Friend?) -> ResultMap? in value.flatMap { (value: Friend) -> ResultMap in value.resultMap } } }, forKey: "friends")
        }
      }

      public struct Friend: GraphQLSelectionSet {
        public static let possibleTypes = ["Human", "Droid"]

        public static let selections: [GraphQLSelection] = [
          GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
          GraphQLField("name", type: .nonNull(.scalar(String.self))),
          GraphQLField("appearsIn", type: .nonNull(.list(.scalar(Episode.self)))),
          GraphQLField("friends", type: .list(.object(Friend.selections))),
        ]

        public private(set) var resultMap: ResultMap

        public init(unsafeResultMap: ResultMap) {
          self.resultMap = unsafeResultMap
        }

        public static func makeHuman(name: String, appearsIn: [Episode?], friends: [Friend?]? = nil) -> Friend {
          return Friend(unsafeResultMap: ["__typename": "Human", "name": name, "appearsIn": appearsIn, "friends": friends.flatMap { (value: [Friend?]) -> [ResultMap?] in value.map { (value: Friend?) -> ResultMap? in value.flatMap { (value: Friend) -> ResultMap in value.resultMap } } }])
        }

        public static func makeDroid(name: String, appearsIn: [Episode?], friends: [Friend?]? = nil) -> Friend {
          return Friend(unsafeResultMap: ["__typename": "Droid", "name": name, "appearsIn": appearsIn, "friends": friends.flatMap { (value: [Friend?]) -> [ResultMap?] in value.map { (value: Friend?) -> ResultMap? in value.flatMap { (value: Friend) -> ResultMap in value.resultMap } } }])
        }

        public var __typename: String {
          get {
            return resultMap["__typename"]! as! String
          }
          set {
            resultMap.updateValue(newValue, forKey: "__typename")
          }
        }

        public var name: String {
          get {
            return resultMap["name"]! as! String
          }
          set {
            resultMap.updateValue(newValue, forKey: "name")
          }
        }

        public var appearsIn: [Episode?] {
          get {
            return resultMap["appearsIn"]! as! [Episode?]
          }
          set {
            resultMap.updateValue(newValue, forKey: "appearsIn")
          }
        }

        public var friends: [Friend?]? {
          get {
            return (resultMap["friends"] as? [ResultMap?]).flatMap { (value: [ResultMap?]) -> [Friend?] in value.map { (value: ResultMap?) -> Friend? in value.flatMap { (value: ResultMap) -> Friend in Friend(unsafeResultMap: value) } } }
          }
          set {
            resultMap.updateValue(newValue.flatMap { (value: [Friend?]) -> [ResultMap?] in value.map { (value: Friend?) -> ResultMap? in value.flatMap { (value: Friend) -> ResultMap in value.resultMap } } }, forKey: "friends")
          }
        }

        public struct Friend: GraphQLSelectionSet {
          public static let possibleTypes = ["Human", "Droid"]

          public static let selections: [GraphQLSelection] = [
            GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
            GraphQLField("name", type: .nonNull(.scalar(String.self))),
          ]

          public private(set) var resultMap: ResultMap

          public init(unsafeResultMap: ResultMap) {
            self.resultMap = unsafeResultMap
          }

          public static func makeHuman(name: String) -> Friend {
            return Friend(unsafeResultMap: ["__typename": "Human", "name": name])
          }

          public static func makeDroid(name: String) -> Friend {
            return Friend(unsafeResultMap: ["__typename": "Droid", "name": name])
          }

          public var __typename: String {
            get {
              return resultMap["__typename"]! as! String
            }
            set {
              resultMap.updateValue(newValue, forKey: "__typename")
            }
          }

          public var name: String {
            get {
              return resultMap["name"]! as! String
            }
            set {
              resultMap.updateValue(newValue, forKey: "name")
            }
          }
        }
      }
    }
  }
}

public final class HumanQueryQuery: GraphQLQuery {
  public let operationDefinition =
    "query humanQuery($id: String!) {\n  human(id: $id) {\n    __typename\n    name\n  }\n}"

  public var id: String

  public init(id: String) {
    self.id = id
  }

  public var variables: GraphQLMap? {
    return ["id": id]
  }

  public struct Data: GraphQLSelectionSet {
    public static let possibleTypes = ["QueryType"]

    public static let selections: [GraphQLSelection] = [
      GraphQLField("human", arguments: ["id": GraphQLVariable("id")], type: .object(Human.selections)),
    ]

    public private(set) var resultMap: ResultMap

    public init(unsafeResultMap: ResultMap) {
      self.resultMap = unsafeResultMap
    }

    public init(human: Human? = nil) {
      self.init(unsafeResultMap: ["__typename": "QueryType", "human": human.flatMap { (value: Human) -> ResultMap in value.resultMap }])
    }

    public var human: Human? {
      get {
        return (resultMap["human"] as? ResultMap).flatMap { Human(unsafeResultMap: $0) }
      }
      set {
        resultMap.updateValue(newValue?.resultMap, forKey: "human")
      }
    }

    public struct Human: GraphQLSelectionSet {
      public static let possibleTypes = ["Human"]

      public static let selections: [GraphQLSelection] = [
        GraphQLField("__typename", type: .nonNull(.scalar(String.self))),
        GraphQLField("name", type: .nonNull(.scalar(String.self))),
      ]

      public private(set) var resultMap: ResultMap

      public init(unsafeResultMap: ResultMap) {
        self.resultMap = unsafeResultMap
      }

      public init(name: String) {
        self.init(unsafeResultMap: ["__typename": "Human", "name": name])
      }

      public var __typename: String {
        get {
          return resultMap["__typename"]! as! String
        }
        set {
          resultMap.updateValue(newValue, forKey: "__typename")
        }
      }

      public var name: String {
        get {
          return resultMap["name"]! as! String
        }
        set {
          resultMap.updateValue(newValue, forKey: "name")
        }
      }
    }
  }
}