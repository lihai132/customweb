import store from "../../store/index.js";
import axios from 'axios';
import encryptUtil from './encryptUtil.js';

function request(url, hd, body) {
    let LogingType = store.state.Login.LogingType;
    let requestPath = store.state.Login.url + url;
    let loginData = {};
    if (LogingType == 3) {
        loginData = store.state.Login.DispatchData;

    }
    if (LogingType == 4) {
        loginData = store.state.Login.CustomerData;

    }

    if (loginData.em == 0) {
        return new Promise((resolve, reject) => {

            axios.post(requestPath, {
                hd: hd,
                bd: body
            })
                .then(res => {
                    // let data = JSON.parse(res.data.body);
                    //console.log(data);
                    // let rid = res.data.hd.rid;
                    resolve(res)

                })
                .catch(error => {
                    reject(error)
                });
        })
    }
    if (loginData.em == 1) {
        let jsonStr = JSON.stringify(body);
        let sk = store.state.Login.key;
        let bd = encryptUtil.encrypt(jsonStr, sk);
        return new Promise((resolve, reject) => {

            axios.post(requestPath, {
                hd: hd,
                bd: bd
            })
                .then(res => {
                    // let data = JSON.parse(res.data.body);
                    //console.log(data);
                    // let rid = res.data.hd.rid;
                    resolve(res)

                })
                .catch(error => {
                    reject(error)
                });
        })
    }




}



export default request;