import React, {useState} from "react";
import {TransactionDetailModal} from "./TransactionDetailModal";
import Button from "antd/es/button";
import {getTransactions} from "../../service/fetchService";
import {errorWithMessage} from "../../service/notification";

export default function TransactionView(props) {

    const [visible, setVisible] = useState(false);
    const [transactionsDetails, setTransactionDetails] = useState();

    const fetchTransactions = () => {
        getTransactions(props.accountId)
            .then(r => setTransactionDetails(r.data))
            .catch(() => errorWithMessage('Could not get transaction data'));
    };

    const showModal = () => {
        fetchTransactions();
        setVisible(true);
    };

    const onModalClose = () => {
        setVisible(false);
    };

    return (
        <div>
            {transactionsDetails !== undefined && <TransactionDetailModal
                onModalClose={onModalClose}
                visible={visible}
                transactionsDetails={transactionsDetails}/>
            }
            <Button onClick={showModal}>History</Button>
        </div>
    )

}
