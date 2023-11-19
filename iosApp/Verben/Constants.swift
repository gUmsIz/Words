//
//  Constants.swift
//  iosApp
//
//  Created by Semih Durmaz on 02.11.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Colors {
    static let primaryColor = Color("PrimaryColor") // #ffe57f
    static let primaryLightColor = Color("PrimaryLightColor") // #ffffb0
    static let primaryDarkColor = Color("PrimaryDarkColor") // #cab350
    static let darkWhiteLightBlackColor = Color(UIColor{ trait in
        return trait.userInterfaceStyle == .dark ? .white : .black
    })
}
struct Size {
    static let cornerRadius = 16.0;
}
struct Texts {
    static let appName = "Verben"
    static let about = "Über App"
    static let aboutInfo = "Quelle für alle Verben und Beispielsätze ist d-seite.de (DaF für Erwachsene)"
    static let ok = "Ok"
    static let url = "https://d-seite.de"
    static let urlButton = "d-seite.de"
    static let textFieldHint = "Geben Sie ein Wort ein"
    static let preparationInfo = "Vorbereitung für den ersten Gebrauch"
    // DetailView
    static let translation = "Übersetzung"
    static let addTranslation = "Übersetzung hinzufügen"
    static let newTranslation = "Neue Übersetzung"
    static let add = "Hinzufügen"
    static let cancel = "Cancel"
    static let conjugation = "Konjuagtionen"
    static let i = "Ich"
    static let you = "Du"
    static let imperative = "(Imperativ)"
    static let presentPerfect = "(Prät.)"
    static let past = "(Perf.)"
    static let pastPerfect = "(Konjunktiv 2)"
    static let heSheIt = "Er-Sie-Es"
    static let have = "habe"
    static let structure = "Strukturen"
    static let samples = "Beispiele"
}
