
import shared
@MainActor
class ViewModel: ObservableObject{
    
    let repo : Repository = RepositoryHelper().getRepo()
    
    var verbList : [WordModel] = []
    var verbListFavorite : [WordModel] = []
    @Published var searchResults: [WordModel] = []
    @Published var favoriteSearchResults: [WordModel] = []
    @Published var searchText: String = "" {
        didSet{
            searchVerbList()
        }
    }
    init(){
        self.loadLaunches()
    }
    private func searchVerbList() {
        self.searchResults = if searchText.isEmpty {
            verbList
        } else {
            verbList.filter { ($0.name.lowercased().contains(searchText.lowercased())) }
        }
        self.favoriteSearchResults = if searchText.isEmpty {
            verbListFavorite
        } else {
            verbListFavorite.filter { $0.name.lowercased().contains(searchText.lowercased()) }
        }
    }
    func loadLaunches() {
        Task {
            do {
                try await repo.loadAllData()
                repo.verbList.watch{ list in
                    guard let verbList = list else {
                                    return
                                }
                    self.verbList = (verbList as? [WordModel?] ?? []).compactMap{$0}
                    self.searchResults = self.verbList
                }
                repo.verbListFavorite.watch{ list in
                    guard let verbListFavorite = list else {
                                    return
                                }
                    self.verbListFavorite = (verbListFavorite as? [WordModel?] ?? []).compactMap{$0}
                    self.favoriteSearchResults = self.verbListFavorite
                }
            } catch {
                print("some error")
            }
        }
    }
    
    func updateFavState(name: String?) {
        if name == nil {return}
        Task{
            try await repo.updateFavoriteStateInDB(name: name!)
        }
    }
}

