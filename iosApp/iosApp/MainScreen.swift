
import SwiftUI
import shared

struct MainScreen: View {
    @EnvironmentObject var viewModel: ViewModel
    @State private var selectedTab: Int = 0
    
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
                        Image(systemName: "heart.fill")
                            .foregroundColor(selectedTab == 1 ? Color.red : Color.black)
                    }
                    .onTapGesture {
                        self.selectedTab = 1
                    }
                    Spacer()
                }
            }
            .padding()
            .background(Colors.primaryColor.edgesIgnoringSafeArea(.all))
            
            TextField("Geben Sie ein Wort ein", text: $viewModel.searchText)
                .padding(.horizontal)
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
    }
}
@available(iOS 17.0, *)
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
@available(iOS 17.0, *)
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
