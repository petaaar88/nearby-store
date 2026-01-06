import androidx.lifecycle.LiveData
import com.met.nearby.store.domain.CategoryModel
import com.met.nearby.store.repository.ResultsRepository

class ResultsViewModel{
    private val repository = ResultsRepository()

    fun loadSubCategory(id: String): LiveData<MutableList<CategoryModel>> {
        return repository.loadSubCategory(id)
    }
}