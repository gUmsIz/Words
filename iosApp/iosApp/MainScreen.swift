
import SwiftUI
import shared

struct MainScreen: View {
    @State private var selectedTab: Int = 0
    @State private var searchText: String = ""
    let verbList: [WordModel?]
    let verbListFavorite: [WordModel?]
    
    var searchResults: [WordModel?] {
        if searchText.isEmpty {
            return verbList
        } else {
            return verbList.filter { $0!.name.lowercased().contains(searchText.lowercased()) }
        }
    }
    
    var favoriteSearchResults: [WordModel?] {
        if searchText.isEmpty {
            return verbListFavorite
        } else {
            return verbListFavorite.filter { $0!.name.lowercased().contains(searchText.lowercased()) }
        }
    }
    var body: some View {
        VStack {
            VStack{
                HStack {
                    Text("Verben").padding(.leading).font(.title)
                    Spacer()
                }
                HStack {
                    Spacer()
                    HStack {
                        Image(systemName: "airplane")
                            .foregroundColor(selectedTab == 0 ? Color.red : Color.black)
                        Text("Verben")
                    }
                    .onTapGesture {
                        self.selectedTab = 0
                    }
                    Spacer()
                    HStack {
                        Image(systemName: "person.fill")
                            .foregroundColor(selectedTab == 1 ? Color.red : Color.black)
                        //Text("Second tab")
                    }
                    .onTapGesture {
                        self.selectedTab = 1
                    }
                    Spacer()
                }
            }
            .padding()
            .background(Colors.primaryColor.edgesIgnoringSafeArea(.all))
            
            TextField("Geben Sie ein Wort ein", text: $searchText)
                .padding(.horizontal)
                .frame(height: 48)
                .background(Colors.primaryLightColor)
                .clipShape(.rect(cornerRadius: 20))
                .overlay( /// apply a rounded border
                    RoundedRectangle(cornerRadius: 20)
                        .stroke(.gray, lineWidth: 1)
                )
                .padding(.horizontal)
            Spacer()
            switch(selectedTab){
            case 0: VerListView(searchResults: searchResults)
            case 1: VerListView(searchResults: favoriteSearchResults)
            default:
                Text("Second")
            }
            
        }
    }
}
struct VerListView: View {
    let searchResults : [WordModel?]
    let primaryLightColor = Color(red: 1, green: 1, blue: 0.69) // #ffffb0
    var body: some View {
        VStack {
            ScrollView {
                LazyVStack(alignment: .leading) {
                    ForEach(searchResults, id: \.self) { wordModel in
                        NavigationLink(destination: DetailScreen(wordModel: wordModel)){
                            Text(wordModel?.name ?? "empty")
                        }
                    }.foregroundColor(.black)
                }
            }
            .padding(.leading)
            .padding(.top)
            .background(primaryLightColor)
            .clipShape(.rect(cornerRadius: 20))
            .overlay(
                RoundedRectangle(cornerRadius: 20)
                    .stroke(.gray, lineWidth: 1)
            )
            .padding(.horizontal)
        }
    }
}

#Preview {
    MainScreen(verbList: [],verbListFavorite: [])
}
