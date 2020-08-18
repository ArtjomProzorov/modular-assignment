import React, {useState} from "react";
import {Tabs} from 'antd';
import CreateAccount from "./account/CreateAccount";
import Accounts from "./account/Accounts";

const {TabPane} = Tabs;


export function TabBar() {

    const [tabKey, setTabKey] = useState('1');

    return (
        <div>
            <Tabs size={'large'}
                  onChange={key => {
                      setTabKey(key)
                  }}
            >
                <TabPane defaultActiveKey={tabKey} tab="View accounts" key='1'>
                    <Accounts tabKey={tabKey}/>
                </TabPane>
                <TabPane tab="Create Account" key="2">
                    <CreateAccount/>
                </TabPane>
            </Tabs>
        </div>
    )

}