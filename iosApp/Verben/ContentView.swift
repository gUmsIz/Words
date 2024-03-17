import SwiftUI
import shared

struct ContentView: View {
    
    @StateObject var viewModel = ViewModel()
    
    var body: some View {
        NavigationStack {
            MainScreen()
        }
        .navigationViewStyle(.stack)
        .environmentObject(viewModel)
        .tint(Colors.darkWhiteLightBlackColor)
    }
    
    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            ContentView(viewModel: .init())
        }
    }
    
}
extension WordModel : Identifiable{}
