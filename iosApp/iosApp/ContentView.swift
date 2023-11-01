import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greet()
    
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        NavigationView {
            MainScreen(verbList: viewModel.verbList,verbListFavorite: viewModel.verbListFavorite)
        }
    }
    
    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            ContentView(viewModel: .init())
        }
    }
    
}
