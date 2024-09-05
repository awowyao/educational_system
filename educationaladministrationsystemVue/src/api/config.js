import axios from 'axios'
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();




//使用axios下面的create([config])方法创建axios实例，其中config参数为axios最基本的配置信息。
const API = axios.create({
	baseURL: 'http://127.0.0.1:8081/', //请求后端数据的基本地址，自定义
	timeout: 5000 ,//请求超时设置，单位ms
})
API.interceptors.request.use(
  config => {
    // 在请求中添加 Token

    const token = cookies.get("token");
    if (token) {
      config.headers.token = `${token}`;
    }
    return config;
  },
  error => {
    // 请求错误时的处理
    console.log(error);
    return Promise.reject(error);
  }
);
//导出我们建立的axios实例模块，ES6 export用法
export default API