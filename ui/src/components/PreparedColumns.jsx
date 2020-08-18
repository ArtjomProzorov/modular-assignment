import BalanceView from "./balance/BalanceView";
import TransactionView from "./transaction/TransactionView";
import React from "react";
import CreateTransactionView from "./transaction/CreateTransactionView";


export const COLUMNS = [
    {
        title: 'Account id', dataIndex: 'accountId', key: 'accountId',
        render: (text, record) =>
            <div>{record.accountId}</div>
    },
    {
        title: 'Customer id', dataIndex: 'customerId', key: 'customerId',
        render: (text, record) =>
            <div>{record.customerId}</div>
    },
    {
        title: 'Country', dataIndex: 'country', key: 'country',
        render: (text, record) =>
            <div>{record.country}</div>
    },
    {
        title: "Balances", dataIndex: 'balances', key: 'balances',
        render: (text, record) => <BalanceView accountId={record.accountId} balances={record.balances}/>
    },
    {
        title: "Transaction", dataIndex: 'transaction', key: 'transactions',
        render: (text, record) => <TransactionView accountId={record.accountId}/>
    },
    {
        title: "Transaction", dataIndex: 'transaction', key: 'createTransaction',
        render: (text, record) => <CreateTransactionView accountId={record.accountId} balances={record.balances}/>
    },
];

export const TRANSACTION_COLUMNS = [

    {
        title: 'Transaction Id', dataIndex: 'transactionId', key: 'transactionId',
        render: (text, record) =>
            <div>{record.transactionId}</div>
    },
    {
        title: 'Amount', dataIndex: 'amount', key: 'amount',
        render: (text, record) =>
            <div>{record.amount}</div>
    },
    {
        title: 'Currency', dataIndex: 'currency', key: 'currency',
        render: (text, record) =>
            <div>{record.currency}</div>
    },
    {
        title: 'Direction', dataIndex: 'direction', key: 'direction',
        render: (text, record) =>
            <div>{record.direction}</div>
    },
    {
        title: 'Description', dataIndex: 'description', key: 'description',
        render: (text, record) =>
            <div>{record.description}</div>
    },
];