package ru.taxcom.feedback.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.taxcom.feedback.api.FeedbackApi
import ru.taxcom.feedback.api.FeedbackApiImpl
import ru.taxcom.feedback.repository.FeedbackRepository
import ru.taxcom.feedback.repository.FeedbackRepositoryImpl
import ru.taxcom.feedback.storage.UuidStorageImpl
import ru.taxcom.feedback.ui.FeedbackScreenFragment
import ru.taxcom.feedback.ui.FeedbackScreenViewModel
import ru.taxcom.feedback.ui.detail.DetailFeedbackFragment
import ru.taxcom.feedback.ui.detail.DetailFeedbackViewModel
import ru.taxcom.feedback.ui.history.FeedbackHistoryFragment
import ru.taxcom.feedback.ui.history.FeedbackHistoryViewModel
import ru.taxcom.feedback.usecase.CreateChatUseCase
import ru.taxcom.feedback.usecase.GetChatUseCase
import ru.taxcom.feedback.usecase.GetFeedbackHistoryUseCase
import ru.taxcom.feedback.usecase.SendMessageUseCase
import ru.taxcom.feedback.usecase.impl.CreateChatUseCaseImpl
import ru.taxcom.feedback.usecase.impl.GetChatUseCaseImpl
import ru.taxcom.feedback.usecase.impl.GetFeedbackHistoryUseCaseImpl
import ru.taxcom.feedback.usecase.impl.SendMessageUseCaseImpl
import ru.taxcom.taxcomkit.di.modules.DocumentUploadModule
import ru.taxcom.taxcomkit.utils.factories.viewmodelfactory.ViewModelKey
import ru.taxcom.taxcomkit.uuid.UuidStorage

@Module(
    includes = [
        DocumentUploadModule::class,
    ]
)
abstract class FeedbackModule {

    @Binds
    abstract fun provideRemoteControlApi(remoteControlApi: FeedbackApiImpl): FeedbackApi

    @Binds
    @IntoMap
    @ViewModelKey(FeedbackScreenViewModel::class)
    abstract fun bindFeedbackScreenViewModel(viewModel: FeedbackScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailFeedbackViewModel::class)
    abstract fun bindDetailFeedbackViewModel(viewModel: DetailFeedbackViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedbackHistoryViewModel::class)
    abstract fun bindFeedbackHistoryViewModel(viewModel: FeedbackHistoryViewModel): ViewModel

    @Binds
    abstract fun bindHistoryUseCase(usecase: GetFeedbackHistoryUseCaseImpl): GetFeedbackHistoryUseCase

    @Binds
    abstract fun bindCreateChatUseCase(usecase: CreateChatUseCaseImpl): CreateChatUseCase

    @Binds
    abstract fun bindSendFeedbackUseCase(usecase: GetChatUseCaseImpl): GetChatUseCase

    @Binds
    abstract fun bindSendMessageUseCase(usecase: SendMessageUseCaseImpl): SendMessageUseCase

    @Binds
    abstract fun bindFeedbackRepository(repository: FeedbackRepositoryImpl): FeedbackRepository

    @ContributesAndroidInjector
    abstract fun bindFeedbackScreen(): FeedbackScreenFragment

    @ContributesAndroidInjector
    abstract fun bindDetailFeedbackFragment(): DetailFeedbackFragment

    @ContributesAndroidInjector
    abstract fun bindFeedbackHistoryFragment(): FeedbackHistoryFragment

    @Binds
    abstract fun bindUuidStorage(storage: UuidStorageImpl): UuidStorage
}