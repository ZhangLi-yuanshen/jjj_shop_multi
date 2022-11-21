import axios from 'axios';
import qs from 'qs';
import router from '@/router';
import store from '@/store';
import {
    Message,
    Loading
} from 'element-ui';
import {getCookie,delCookie,deleteSessionStorage} from '@/utils/base.js';

//axios.defaults.timeout = 5000;                        //响应时间
axios.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'; //配置请求头
axios.defaults.baseURL = '/api'; //配置接口地址
axios.defaults.withCredentials = true;
axios.defaults.responseType = 'json';

//POST传参序列化(添加请求拦截器)
axios.interceptors.request.use((config) =>
{
    const isLogin = getCookie('isLogin_supplier');
    const token = getCookie('token_supplier');
    if (isLogin && token) {
      config.headers['token'] = token
    }
    return config;
}, (error) =>
{
    console.log('错误的传参')
    return Promise.reject(error);
});

//返回状态判断(添加响应拦截器)
axios.interceptors.response.use((res) =>
{
    //未成功处理
    if (res.data.code !== 1) {
        if (res.data.code === 0) {
            Message({
                showClose: true,
                message: res.data.msg,
                type: "error"
            });
            return Promise.reject(res.data);
        } else {
            delCookie('isLogin_supplier');
            delCookie('baseInfo_supplier');
            deleteSessionStorage('rolelist_supplier');
            deleteSessionStorage('authlist_supplier');
            /*删除stores数据*/
            store.commit('user/setState',{key:'roles',val:null});
            router.push({
                path: '/login',
                query: {}
            });
           return Promise.reject(res.data);
        }
    }
    return res.data;
}, (error) =>
{
    Message({
        showClose: true,
        message: '接口请求异常，请稍后再试~',
        type: "error"
    });
    return Promise.reject(error);
});

/**
 * 返回一个Promise(发送post请求)
 * errorback是否错误回调
 */
export function _post(url, params, errorback)
{
    params = qs.stringify(params);
    return new Promise((resolve, reject) =>
    {
        axios.post(url, params)
            .then(response =>
            {
                resolve(response);
            })
            .catch((error) =>
            {
                reject(error);
            })
    })
}

/**
 * 返回一个Promise(发送get请求)
 * errorback是否错误回调
 */
export function _get(url, param, errorback)
{
    return new Promise((resolve, reject) =>
    {
        axios.get(url, {
            params: param
        })
            .then(response =>
            {
                resolve(response)
            })
            .catch((error) =>
            {
                reject(error);
            })
    })
}

/**
 * 返回一个Promise(发送上传请求)
 * errorback是否错误回调
 */
export function _upload(url, formData, errorback)
{
    return new Promise((resolve, reject) =>
    {
        axios.post(url, formData, {"Content-Type": "multipart/form-data"})
            .then(response =>
            {
                resolve(response);
            })
            .catch((error) =>
            {
                reject(error);
            })
    })
}

/**
 * 返回一个Promise(发送post请求)
 * errorback是否错误回调
 */
export function _postBody(url, params, errorback) {
  return new Promise((resolve, reject) => {
    axios.post(url, params, {headers: {'Content-Type': 'application/json;charset=UTF-8'}})
      .then(response => {
        resolve(response);
      })
      .catch((error) => {
        errorback && reject(error);
      })
  })
}

export default {
    _post,
    _get,
    _upload,
    _postBody
}
