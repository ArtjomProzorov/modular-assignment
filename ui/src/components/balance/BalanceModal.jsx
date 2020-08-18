import React from "react";
import {Button, Col, Modal, Row, Statistic} from 'antd';


export function BalanceModal(props) {

    const {visible, onModalClose,balances} = props;


    return (
        <div>
            <Modal width={600}
                   visible={visible}
                   onCancel={onModalClose}
                   footer={<Button onClick={onModalClose}>Close</Button>}
            >
                {balances.map(b => <Row gutter={16}>
                        <Col span={12}>
                            <Statistic title="Currency" value={b.currency}/>
                        </Col>
                        <Col span={12}>
                            <Statistic title="Amount" value={b.amount} precision={2}/>
                        </Col>
                    </Row>
                )}
            </Modal>
        </div>
    )
}

