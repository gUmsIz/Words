
import shared
@MainActor
class ViewModel: ObservableObject{
    
    let repo : Repository = RepositoryHelper().getRepo()
    
    @Published var verbList : [WordModel?] = []
    @Published var verbListFavorite : [WordModel?] = []
    init(){
        self.loadLaunches()
    }
    
    func loadLaunches() {
        Task {
            do {
                try await repo.loadAllData()
                repo.verbList.watch{ list in
                    guard let verbList = list else {
                                    return
                                }
                    self.verbList = verbList as? [WordModel?] ?? []
                    
                }
                repo.verbListFavorite.watch{ list in
                    guard let verbListFavorite = list else {
                                    return
                                }
                    self.verbListFavorite = verbListFavorite as? [WordModel?] ?? []
                    
                }
            } catch {
                print("some error")
            }
        }
    }
}
