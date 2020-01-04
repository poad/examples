import { Module, VuexModule, Mutation } from 'vuex-module-decorators'

@Module({
    name: 'authmodule',
    stateFactory: true,
    namespaced: true,
  })
class AuthModule extends VuexModule {

}
