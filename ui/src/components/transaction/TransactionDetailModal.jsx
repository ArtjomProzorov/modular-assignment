import Modal from "antd/es/modal";
import Button from "antd/es/button";
import React, {useState} from "react";
import {Table} from 'antd';
import Search from "antd/es/input/Search";
import {TRANSACTION_COLUMNS} from "../PreparedColumns";
import styled from "styled-components";

const StyledTable = styled(Table)`
  .ant-table-tbody > tr:hover td {
  background: aliceblue !important;
}
`;

export function TransactionDetailModal(props) {

    const {visible, onModalClose, transactionsDetails} = props;
    const [filteredTabled, setFilteredTable] = useState([]);

    function searchAccount(e) {
        setFilteredTable(transactionsDetails.filter(o => o.transactionId === parseInt(e.target.value)));
    }

    return (
        <div>
            <Modal width={900}
                   visible={visible}
                   onCancel={onModalClose}
                   footer={<Button onClick={onModalClose}>Close</Button>}
            >
                <Search onChange={e => searchAccount(e)}
                        style={{width: '30%', height: '35px'}}
                        placeholder={'Search by Transaction Id'}
                />
                <StyledTable style={{marginTop: 10}} columns={TRANSACTION_COLUMNS}
                             size="small"
                             dataSource={filteredTabled.length > 0 ? filteredTabled : transactionsDetails}
                             rowKey="transactionId"
                             pagination={{
                                 hideOnSinglePage: true,
                                 defaultPageSize: 5
                             }}
                />
            </Modal>
        </div>
    )

}

