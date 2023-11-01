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
            Text("\(wordModel?.name ?? "")")
            Spacer()
        }
    }
}

#Preview {
    DetailScreen(wordModel: nil)
}
