package com.dimi.moviedatabase.business.interactors.movie

import com.dimi.moviedatabase.business.data.network.ApiResponseHandler
import com.dimi.moviedatabase.business.data.network.abstraction.MovieNetworkDataSource
import com.dimi.moviedatabase.business.data.util.safeApiCall
import com.dimi.moviedatabase.business.domain.model.Image
import com.dimi.moviedatabase.business.domain.state.*
import com.dimi.moviedatabase.business.interactors.SharedUseCasesKeys.USE_CASE_IMAGES
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import com.dimi.moviedatabase.business.domain.state.ViewState as ViewStateInterface

class MovieImagesUseCase(
    private val networkDataSource: MovieNetworkDataSource
) {

    fun <ViewState : ViewStateInterface> getResults(
        viewState: ViewState,
        movieId: Int,
        stateEvent: StateEvent
    ) = flow {

        val result = safeApiCall(IO) {
            networkDataSource.getMovieImages(
                movieId
            )
        }

        val request = object : ApiResponseHandler<ViewState, List<Image>>(
            response = result,
            stateEvent = stateEvent
        ) {
            override suspend fun handleSuccess(responseValue: List<Image>?): DataState<ViewState>? {

                return if (responseValue.isNullOrEmpty())
                    DataState.data(
                        response = Response(
                            message = RETRIEVING_MOVIE_IMAGES_FAILED,
                            messageType = MessageType.Success,
                            uiComponentType = UIComponentType.None
                        ),
                        data = viewState,
                        stateEvent = stateEvent
                    )
                else {
                    viewState.setData(
                        hashMapOf(
                            USE_CASE_IMAGES to responseValue
                        )
                    )
                    DataState.data(
                        response = Response(
                            message = USE_CASE_IMAGES,
                            messageType = MessageType.Success,
                            uiComponentType = UIComponentType.None
                        ),
                        data = viewState,
                        stateEvent = stateEvent
                    )
                }
            }

        }.getResult()

        emit(request)
    }

    companion object {
        const val RETRIEVING_MOVIE_IMAGES_FAILED =
            "Failed retrieving images for this movie."
    }
}