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
            ScrollView {
                VStack(alignment: .leading) {
                    Translation(wordModel: wordModel)
                    Konjugation(wordModel: wordModel)
                    Structure(wordModel: wordModel)
                    Samples(wordModel: wordModel)
                }
            }
        }
        .toolbar{
            ToolbarItem(placement: .principal) {
                Text(wordModel?.name.capitalized ?? "")
            }
            ToolbarItem(placement: .topBarTrailing) {
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
        }
        .toolbarBackground(Colors.primaryColor, for: .navigationBar)
        .toolbarBackground(.visible, for: .navigationBar)
    }
}

#Preview {
    DetailScreen(wordModel: nil)
}

struct Translation: View {
    @EnvironmentObject var viewModel: ViewModel
    let wordModel: WordModel?
    @State private var isDialogVisible = false;
    @State private var translation = "";
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text("Übersetzung")
                    .padding()
                    .background(Colors.primaryColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: Size.cornerRadius)
                            .stroke(Colors.primaryColor, lineWidth: 1)
                    )
                Spacer()
                Image(systemName: "plus")
                    .foregroundColor(.green)
                    .padding()
                    .background(Colors.primaryColor)
                    .clipShape(.rect(
                        topLeadingRadius: 0,
                        bottomLeadingRadius: Size.cornerRadius,
                        bottomTrailingRadius: 0,
                        topTrailingRadius: 0))
                    .overlay(
                        RoundedRectangle(cornerRadius: Size.cornerRadius)
                            .stroke(Colors.primaryColor, lineWidth: 1)
                    )
                    .onTapGesture {
                        isDialogVisible.toggle()
                    }
                    .alert("Add Translation",isPresented: $isDialogVisible){
                        TextField("New translation", text: $translation).textInputAutocapitalization(.never)
                            .padding(.horizontal)
                        Button("Add", action: {
                            if !translation.isEmpty {
                                wordModel?.translation.add(translation)
                                viewModel.updateFavState(wordModel: wordModel)
                                translation = ""
                                isDialogVisible = false
                            }
                        })
                        Button("Cancel", role: .cancel) { }
                    }
                
                
            }
            ForEach(getTranslationList(),id: \.self){
                item in
                HStack {
                    Text("- \(item)")
                    Spacer()
                    Image(systemName: "trash")
                        .contentTransition(.symbolEffect(.replace))
                        .onTapGesture {
                            withAnimation {
                                wordModel?.translation.remove(item)
                                viewModel.updateFavState(wordModel: wordModel)
                            }
                        }
                }.padding(.horizontal)
            }.padding(.bottom)
        }
        .roundedBorder(color:Colors.primaryLightColor)
        .padding()
        .shadow(radius: 5)
    }
    //TODO write extension
    private func getTranslationList() -> [String] {
        var list:[String] = []
        for word in wordModel!.translation{
            list.append(word as! String)
        }
        return list
    }
}

struct Konjugation: View {
    let wordModel: WordModel?
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text("Konjuagtionen")
                    .padding()
                    .background(Colors.primaryColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: Size.cornerRadius)
                            .stroke(Colors.primaryColor, lineWidth: 1)
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
            }.padding([.leading , .bottom])
        }
        .roundedBorder(color:Colors.primaryLightColor)
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
                    .background(Colors.primaryColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: Size.cornerRadius)
                            .stroke(Colors.primaryColor, lineWidth: 1)
                    )
                Spacer()
            }
            
            ForEach(getStructureList(),id: \.self){words in
                Text(words).padding(.horizontal)
            }.padding(.bottom)
        }
        .roundedBorder(color:Colors.primaryLightColor)
        .padding()
        .shadow(radius: 5)
    }
    
    private func getStructureList() -> [String] {
        var list:[String] = []
        for word in wordModel!.structure!{
            list.append(word as! String)
        }
        return list
    }
}
struct Samples: View {
    let wordModel: WordModel?
    var body: some View {
        VStack(alignment: .leading){
            HStack{
                Text("Beispiele")
                    .padding()
                    .background(Colors.primaryColor)
                    .overlay(
                        RoundedRectangle(cornerRadius: Size.cornerRadius)
                            .stroke(Colors.primaryColor, lineWidth: 1)
                    )
                Spacer()
            }
            
            ForEach(getSampleList(),id: \.self){words in
                Text(words).padding(.horizontal)
            }.padding(.bottom)
        }
        .roundedBorder(color:Colors.primaryLightColor)
        .padding()
        .shadow(radius: 5)
    }
    private func getSampleList() -> [String] {
        var list:[String] = []
        for word in wordModel!.sampleSentence!{
            list.append(word as! String)
        }
        return list
    }
}
