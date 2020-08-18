import {Table} from 'antd';
import React, {useEffect, useState} from "react";
import styled from "styled-components";
import {COLUMNS} from "../PreparedColumns";
import Search from 'antd/es/input/Search';
import {getAccounts} from "../../service/fetchService";


const StyledTable = styled(Table)`
  .ant-table-tbody > tr:hover td {
  background: aliceblue !important;
}
`;

export function Accounts(props) {

    const [filteredTabled, setFilteredTable] = useState([]);
    const [accounts, setAccounts] = useState([]);

    const fetchAccounts = () => {
        getAccounts()
            .then(res => setAccounts(res.data))
            .catch(err => console.log(err));
    };


    useEffect(() => {
        props.tabKey === '1' && fetchAccounts();
    }, [props.tabKey]);

    function searchAccount(e) {
        setFilteredTable(accounts.filter(o => o.accountId === parseInt(e.target.value)));
    }

    return (
        <>
            <Search
                onChange={e => searchAccount(e)}
                style={{width: '20%', height: '35px'}}
                placeholder={'Search by Account Id'}
            />
            <StyledTable style={{marginTop: 10}} columns={COLUMNS}
                         size="small"
                         dataSource={filteredTabled.length > 0 ? filteredTabled : accounts}
                         rowKey="accountId"
                         pagination={{
                             hideOnSinglePage: true,
                             defaultPageSize: 5
                         }}
            />
        </>
    );
}

export default Accounts;