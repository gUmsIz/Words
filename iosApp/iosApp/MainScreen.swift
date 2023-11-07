
import SwiftUI
import shared

struct MainScreen: View {
    @Environment(\.openURL) var openURL
    @EnvironmentObject var viewModel: ViewModel
    @State private var selectedTab: Int = 0
    @State private var isDialogVisible = false;
    var body: some View {
        GeometryReader { geometry in
            VStack {
                VStack{
                    HStack {
                        HStack {
                            Text("Verben")
                        }
                        .frame(minWidth: 0, maxWidth:.infinity)
                        .contentShape(.rect)
                        .onTapGesture {
                            self.selectedTab = 0
                        }
                        HStack {
                            Image(systemName: selectedTab == 1 ? "heart.fill" : "heart")
                                .contentTransition(.symbolEffect(.replace))
                        }
                        .frame(minWidth: 0, maxWidth:.infinity)
                        .contentShape(.rect)
                        .onTapGesture {
                            self.selectedTab = 1
                        }
                    }
                }
                .padding()
                .overlay(alignment: (selectedTab == 0) ? .bottomLeading :.bottomTrailing){
                    Divider()
                        .frame(width: geometry.size.width/2,height: 2)
                        .background(.white).animation(.easeInOut, value: selectedTab)
                }
                .overlay(alignment: .top){
                    Divider()
                        .shadow(radius: 5)
                }
                .background(Colors.primaryColor.edgesIgnoringSafeArea(.all))
                
                TextField("Geben Sie ein Wort ein", text: $viewModel.searchText)
                    .padding(.horizontal)
                    .textInputAutocapitalization(.never)
                    .frame(height: 48)
                    .background(Colors.primaryLightColor)
                    .clipShape(.rect(cornerRadius: 20))
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(.gray, lineWidth: 1)
                    )
                    .padding(.horizontal)
                Spacer()
                switch(selectedTab){
                case 0: VerListView()
                case 1: FavVerListView()
                default:
                    Text("Second")
                }
            }
            .toolbar{
                ToolbarItem(placement: .topBarLeading) {
                    Text("Verben")
                }
                ToolbarItem(placement: .topBarTrailing){
                    Menu {
                        Button("Über App") {
                            isDialogVisible.toggle()
                        }
                    } label: {
                        Image(systemName: "ellipsis").foregroundColor(.black)
                    }.alert("Quelle für alle Verben und Beispielsätze ist d-seite.de (DaF für Erwachsene)",isPresented: $isDialogVisible){
                        Button("Ok",role:.cancel) {
                            
                        }
                        Button("d-seite.de") {
                            openURL(URL(string: "https://d-seite.de")!)
                        }
                    }
                }
            }
        }
    }
}

struct VerListView: View {
    @EnvironmentObject var viewModel: ViewModel
    var body: some View {
        VStack {
            ScrollView {
                LazyVStack(alignment: .leading) {
                    ForEach(viewModel.searchResults) { wordModel in
                        NavigationLink(destination: DetailScreen(
                            wordModel: wordModel)){
                                Text(wordModel.name)
                            }
                    }.foregroundColor(.black)
                }
            }
            .padding(.leading)
            .padding(.top)
            .background(Colors.primaryLightColor)
            .clipShape(.rect(cornerRadius: 20))
            .overlay(
                RoundedRectangle(cornerRadius: 20)
                    .stroke(.gray, lineWidth: 1)
            )
            .padding(.horizontal)
        }
    }
}

struct FavVerListView: View {
    @EnvironmentObject var viewModel: ViewModel
    var body: some View {
        VStack {
            ScrollView {
                LazyVStack(alignment: .leading) {
                    ForEach(viewModel.favoriteSearchResults) { wordModel in
                        NavigationLink(destination: DetailScreen(
                            wordModel: wordModel)){
                                Text(wordModel.name)
                            }
                    }.foregroundColor(.black)
                }
            }
            .padding(.leading)
            .padding(.top)
            .background(Colors.primaryLightColor)
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
    MainScreen()
}
