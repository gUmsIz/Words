import SwiftUI
import shared

struct ContentView: View {
    let greet = Greeting().greet()
    
    @ObservedObject private(set) var viewModel: ViewModel
    
    var body: some View {
        NavigationView {
            TabbedView(verbList: viewModel.verbList)
            //Text("\(viewModel.verbList.count)")
            /*ScrollView {
                LazyVStack(alignment: .leading) {
                    ForEach(viewModel.verbList, id: \.self) { wordModel in
                        Text(wordModel?.name ?? "empty")
                    }
                }
            }*/
        }
    }
    
    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            ContentView(viewModel: .init())
        }
    }
    
    @MainActor
    class ViewModel: ObservableObject{
        
        let repo : Repository = RepositoryHelper().getRepo()
        
        @Published var verbList : [WordModel?] = []
        init(){
            self.loadLaunches()
        }
        
        func loadLaunches() {
            Task {
                do {
                    try await repo.loadAllData()
                    repo.verbList.watch{ verblist in
                        guard let colorHex = verblist else {
                                        return
                                    }
                        //self.verbList.append(contentsOf: colorHex as! [WordModel?])
                        self.verbList = colorHex as? [WordModel?] ?? []
                        
                    }
                } catch {
                    print("some error")
                }
            }
        }
    }
    
    struct TabbedView: View {
        
        @State private var selectedTab: Int = 0
        
        let verbList: [WordModel?]
        
        var body: some View {
            VStack {
                Picker("", selection: $selectedTab) {
                    Text("Verben").tag(0)
                    Text("Favorite").tag(1)
                }
                .pickerStyle(.segmented)
                
                switch(selectedTab) {
                case 0: ScrollView {
                    LazyVStack(alignment: .leading) {
                        ForEach(verbList, id: \.self) { wordModel in
                            NavigationLink(destination: Text(wordModel?.name ?? "empty")){
                                Text(wordModel?.name ?? "empty")
                            }
                        }
                    }
                }
                case 1: Text("Second4").frame(maxWidth:.infinity,maxHeight: .infinity)
                default:
                    Text("Second")
                }
            }
            .frame(maxWidth:.infinity,maxHeight: .infinity)
        }
    }
}
