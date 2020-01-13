import { GetterTree, ActionTree, MutationTree } from 'vuex'
import { getAccessorType } from 'typed-vuex'

export enum WebAuthnPage {
    Index = "Index",
    SignUp = "SignUp",
    SignIn = "SignIn"
}

export const state = () => ({
    page: WebAuthnPage.SignIn
})
  
export type RootState = ReturnType<typeof state>

export const getters: GetterTree<RootState, RootState> = {
  name: state => state.page,
}

export const mutations: MutationTree<RootState> = {
  CHANGE_PAGE: (state, page: WebAuthnPage) => (state.page = page),
}

export const actions: ActionTree<RootState, RootState> = {
  changePage({ commit }, page: WebAuthnPage) {
    commit('CHANGE_PAGE', page)
  },
}  

// This compiles to nothing and only serves to return the correct type of the accessor
export const accessorType = getAccessorType({
    state,
    getters,
    mutations,
    actions,
    modules: {
      // The key (submodule) needs to match the Nuxt namespace (e.g. ~/store/submodule.ts)
    },
  })
  