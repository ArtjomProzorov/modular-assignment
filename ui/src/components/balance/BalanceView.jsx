import React, {useState} from "react";
import Button from "antd/es/button";
import {BalanceModal} from "./BalanceModal";
import {errorWithMessage} from "../../service/notification";
import {getBalances} from "../../service/fetchService";


export default function BalanceView(props) {

    const [visible, setVisible] = useState(false);
    const [balances, setBalances] = useState([]);

    const fetchBalances = () => {
        getBalances(props.accountId)
            .then(r => setBalances(r.data))
            .catch(() => errorWithMessage('Could not get balance'));
    };

    const showBalanceModal = () => {
        fetchBalances();
        setVisible(true);
    };

    const onModalClose = () => {
        setVisible(false);
    };

    return (
        <div>
            {balances !== undefined && <BalanceModal
                onModalClose={onModalClose}
                visible={visible}
                balances={balances}/>
            }
            <Button onClick={showBalanceModal}>View</Button>
        </div>
    )

}
