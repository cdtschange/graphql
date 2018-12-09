//
//  ViewController.swift
//  graphql
//
//  Created by 毛蔚 on 2018/11/30.
//  Copyright © 2018 山天大畜. All rights reserved.
//

import UIKit
import Apollo

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let query1 = HeroNameQueryQuery()
        apollo.fetch(query: query1) { result, error in
            let hero = result?.data?.hero
            print(hero?.name ?? "")
        }
        
        let query2 = NestedQueryQuery()
        apollo.fetch(query: query2) { result, error in
            let hero = result?.data?.hero
            print(hero?.name ?? "")
        }
        
        let query3 = HumanQueryQuery(id: "1003")
        apollo.fetch(query: query3) { result, error in
            let hero = result?.data?.human
            print(hero?.name ?? "")
        }
    }


}

