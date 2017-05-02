package hr.danijelpopic.github.network;


import hr.danijelpopic.github.model.RepositoriesResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {

    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/search/repositories")
    Observable<RepositoriesResponse> searchRepositories(@Query("q") String query);
}
