//
//  DetailScreen.swift
//  iosApp
//
//  Created by Semih Durmaz on 01.11.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared
struct DetailScreen: View {
    let wordModel: WordModel?
    var body: some View {
        VStack{
            HStack {
                Text("Verben").padding(.leading).font(.title)
                Spacer()
                Image(systemName: "person.fill").padding(.trailing).onTapGesture {
                    
                }
            }
            .background(Color.yellow.edgesIgnoringSafeArea(.all))
            Text("\(wordModel?.name.uppercased() ?? "")")
            Spacer()
            Translation()
            Konjugation(wordModel: wordModel)
            Structure(wordModel: wordModel)
            Samples(wordModel: wordModel)
        }
    }
}

#Preview {
    DetailScreen(wordModel: nil)
}

struct Translation: View {
    var body: some View {
        VStack{
            HStack{
                Text("Übersetzung")
                    .padding()
                    .background(Colors.primaryDarkColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(Colors.primaryDarkColor, lineWidth: 1)
                    )
                Spacer()
                Image(systemName: "person.fill")
            }
        }
        .background(Colors.primaryLightColor)
        .clipShape(.rect(cornerRadius: 20))
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(Colors.primaryLightColor, lineWidth: 1)
        )
        .padding()
        .shadow(radius: 5)
    }
}

struct Konjugation: View {
    let wordModel: WordModel?
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text("Konjuagtionen")
                    .padding()
                    .background(Colors.primaryDarkColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(Colors.primaryDarkColor, lineWidth: 1)
                    )
                Spacer()
            }
            VStack(alignment: .leading){
                Text("Ich \(wordModel?.firstSg ?? "")")
                Text("Du \(wordModel?.secondSg ?? "")")
                Text("\(wordModel?.imp ?? "") (Imperativ)")
                Text("Ich / Er-Sie-Es \(wordModel?.pret ?? "") (Prät.)")
                Text("Ich habe \(wordModel?.perfSg ?? "") (Perf.)")
                Text("\(wordModel?.konj2FSg ?? "") (Konjunktiv 2)")
            }.padding(.leading)
        }
        .background(Colors.primaryLightColor)
        .clipShape(.rect(cornerRadius: 20))
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(Colors.primaryLightColor, lineWidth: 1)
        )
        .padding()
        .shadow(radius: 5)
    }
}
struct Structure: View {
    let wordModel: WordModel?
    var body: some View {
        VStack{
            HStack{
                Text("Strukturen")
                    .padding()
                    .background(Colors.primaryDarkColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(Colors.primaryDarkColor, lineWidth: 1)
                    )
                Spacer()
            }
        }
        .background(Colors.primaryLightColor)
        .clipShape(.rect(cornerRadius: 20))
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(Colors.primaryLightColor, lineWidth: 1)
        )
        .padding()
        .shadow(radius: 5)
    }
}
struct Samples: View {
    let wordModel: WordModel?
    var body: some View {
        VStack{
            HStack{
                Text("Beispiele")
                    .padding()
                    .background(Colors.primaryDarkColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(Colors.primaryDarkColor, lineWidth: 1)
                    )
                Spacer()
            }
        }
        .background(Colors.primaryLightColor)
        .clipShape(.rect(cornerRadius: 20))
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(Colors.primaryLightColor, lineWidth: 1)
        )
        .padding()
        .shadow(radius: 5)
    }
}
