import Vuex from 'vuex';
import Vue from 'vue';

Vue.use(Vuex);

// store 생성
export const store = new Vuex.Store({
  state: {
    token: null,
  },
  getters: {
    isLogin(state) {
      return state.token == null ? false : true;
    },
  },
  mutations: {
    // commit 으로 부를 수 있다.
    setToken(state, _token) {
      state.token = _token;
    },
  },
  actions: {
    // dispatch 로 부를 수 있다.
    setToken: ({ commit }, _token) => {
      commit('setToken', _token);
    },
  },
});
