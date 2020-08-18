import axios from 'axios'

const base = 'http://localhost:8080/account';

export const createAccount = (bundle) => {
    return axios.post(base + '/create', bundle)
};

export const getBalances = (accountId) => {
    return axios.get(base + `/get/${accountId}/balances`);
};

export const getAccounts = () => {
    return axios.get(base + `/get/accounts`);
};

export const getTransactions = (accountId) => {
    return axios.get(base + `/${accountId}/transactions`)
};

export const createTransaction = (transactionBody) => {
    return axios.post(base + `/create/transaction`, transactionBody)
};
