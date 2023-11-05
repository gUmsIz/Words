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
    @EnvironmentObject var viewModel: ViewModel
    let wordModel: WordModel?
    var body: some View {
        VStack{
            HStack {
                Text("Verben").padding(.leading).font(.title)
                Spacer()
                Image(systemName: wordModel!.favorite ? "heart.fill":"heart")
                    .contentTransition(.symbolEffect(.replace))
                    .padding(.trailing).foregroundColor(
                        wordModel!.favorite ? Color.red:Color.black
                    )
                    .onTapGesture {
                        withAnimation {
                            wordModel!.favorite = !wordModel!.favorite
                        }
                        viewModel.updateFavState(wordModel: wordModel)
                    }
            }
            .background(Colors.primaryColor.edgesIgnoringSafeArea(.all))
            ScrollView {
                VStack(alignment: .leading) {
                    Text("\(wordModel?.name.uppercased() ?? "")").padding(.horizontal)
                    Translation(wordModel: wordModel)
                    Konjugation(wordModel: wordModel)
                    Structure(wordModel: wordModel)
                    Samples(wordModel: wordModel)
                }
            }
        }
    }
}

#Preview {
    DetailScreen(wordModel: nil)
}

struct Translation: View {
    @EnvironmentObject var viewModel: ViewModel
    let wordModel: WordModel?
    @State var isDialogVisible = false;
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
                Image(systemName: "plus")
                    .foregroundColor(.green)
                    .padding()
                    .background(Colors.primaryDarkColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(Colors.primaryDarkColor, lineWidth: 1)
                    )
                    .clipShape(.rect(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: 20,
                        bottomTrailingRadius: 0,
                        topTrailingRadius: 0))
                    .onTapGesture {
                        print("size :\(wordModel!.translation.count)")
                        isDialogVisible.toggle()
                    }
                    .alert("Hello",isPresented: $isDialogVisible){
                        Text("HHH")
                        Button("OK", action: {})
                        Button("Cancel", role: .cancel) { }
                    } message: {
                        Text("GG")
                    }
                /*ForEach(wordModel?.translation, id:\.self) { item in
                 Text(item)
                 }*/
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
        VStack(alignment: .leading){
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
            
            ForEach(getStructureList(),id: \.self){words in
                Text(words).padding(.horizontal)
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
    
    func getStructureList() -> [String] {
        var a:[String] = []
        for word in wordModel!.structure!{
            a.append(word as! String)
        }
        return a
    }
}
struct Samples: View {
    let wordModel: WordModel?
    var body: some View {
        VStack(alignment: .leading){
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
            
            ForEach(getSampleList(),id: \.self){words in
                Text(words).padding(.horizontal)
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
    func getSampleList() -> [String] {
        var a:[String] = []
        for word in wordModel!.sampleSentence!{
            a.append(word as! String)
        }
        return a
    }
}
