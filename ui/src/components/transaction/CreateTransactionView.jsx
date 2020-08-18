import React, {useState} from "react";
import Button from "antd/es/button";
import {CreateTransactionModal} from "./CreateTransactionModal";
import {getBalances} from "../../service/fetchService";
import {errorWithMessage} from "../../service/notification";

export default function CreateTransactionView(props) {

    const [visible, setVisible] = useState(false);
    const [balances, setBalances] = useState();

    const fetchBalances = () => {
        getBalances(props.accountId)
            .then(r => setBalances(r.data))
            .catch(() => errorWithMessage('Could not get balance'));
    };



    const showModal = () => {
        fetchBalances();
        setVisible(true);
    };

    const onModalClose = () => {
        setVisible(false);
    };

    return (
        <div>
            {balances !== undefined && <CreateTransactionModal
                onModalClose={onModalClose}
                accountId={props.accountId}
                visible={visible}
                balances={balances}/>
            }
            <Button onClick={showModal}>Create</Button>
        </div>
    )

}
