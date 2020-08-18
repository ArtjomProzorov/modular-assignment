import React, {useState} from "react";
import {Button, Modal, Radio, Select} from 'antd';
import Form from "antd/es/form";
import Input from "antd/es/input";
import {createTransaction} from "../../service/fetchService";
import {errorWithMessage, success} from "../../service/notification";


const formItemLayout = {
    labelCol: {
        xs: {span: 24},
        sm: {span: 5},
    },
    wrapperCol: {
        xs: {span: 24},
        sm: {span: 22},
    },
};

const directionType = [
    {
        label: 'IN',
        value: 'IN'
    },
    {
        label: 'OUT',
        value: 'OUT'
    }
];

export function CreateTransactionModal(props) {

    const {visible, onModalClose} = props;
    const [currency, setCurrency] = useState('');
    const [amount, setAmount] = useState('');
    const [direction, setDirection] = useState('');
    const [description, setDescription] = useState('');
    const [receiverId, setReceiverId] = useState('');
    const [form] = Form.useForm();


    function resetState() {
        setReceiverId('');
        setDescription('');
        setAmount('');
        setDirection('');
        setDescription('')
    }


    const postTransaction = () => {
        const bundle = {
            receiverId: receiverId,
            accountId: props.accountId,
            currency: currency,
            amount: amount,
            direction: direction,
            description: description
        };

        if (props.accountId && currency && amount && direction && description !== '') {
            createTransaction(bundle).then(() => {
                onModalClose();
                resetState();
                form.resetFields();
                success('Transaction successfully were made')
            })
                .catch(() => errorWithMessage('Transaction failed'));
        }
    };

    return (
        <div>
            <Modal title={'Create transaction'}
                   width={'70%'}
                   visible={visible}
                   onCancel={() => {
                       onModalClose();
                       form.resetFields();
                       resetState();
                   }}
                   footer={<Button onClick={onModalClose}>Close</Button>}
            >
                <Form form={form} {...formItemLayout}
                      style={{width: '50%'}}
                      size={"large"}
                >
                    <Form.Item label="Currency" name="currency"
                               rules={[{required: true, message: 'Please enter choose currency'}]}>
                        <Select notFoundContent={
                            <div>CURRENCY NOT SUPPORTED JUST YET</div>
                        }
                                placeholder="Please choose currency"
                                value={currency}
                                onChange={setCurrency}
                        >
                            {props.balances.map(b => (
                                <Select.Option key={b.currency} value={b.currency}>
                                    {b.currency}
                                </Select.Option>
                            ))}
                        </Select>

                    </Form.Item>
                    <Form.Item label="Direction" name="direction"
                               rules={[{required: true, message: 'Choose direction please '}]}>
                        <Radio.Group
                            options={directionType}
                            value={direction}
                            onChange={e => setDirection(e.target.value)}
                            optionType="button"
                        />
                    </Form.Item>
                    {direction === 'OUT' ?
                        <Form.Item onChange={e => setReceiverId(e.target.value)}
                                   label="Receiver id"
                                   name="receiver"
                                   rules={[{required: true, message: 'Id should be specified'},
                                       () => ({
                                           validator(rule, value) {
                                               if (!RegExp("^(\\d+$)").test(value)) {
                                                   return Promise.reject('Receiver account id must be a number');
                                               }
                                               if (value <= 0) {
                                                   return Promise.reject('Customer id must be a positive number');
                                               }
                                           }
                                       })
                                   ]}>
                            <Input/>
                        </Form.Item> : <div/>}
                    <Form.Item onChange={e => setAmount(e.target.value)}
                               label="Amount"
                               name="amount"
                               rules={[{required: true, message: 'Enter the amount please'},
                                   () => ({
                                       validator(rule, value) {
                                           if (value === '' || !RegExp("^([\\d.]+$)").test(value)) {
                                               return Promise.reject('Invalid amount');
                                           }
                                           if (direction === 'OUT' && props.balances.some(b => b.currency === currency && b.amount < value)) {
                                               return Promise.reject("Invalid balance in " + currency)
                                           }
                                       }
                                   })
                               ]}>
                        <Input/>
                    </Form.Item>
                    <Form.Item onChange={e => setDescription(e.target.value)}
                               label="Description"
                               name="description"
                               rules={[{required: true, message: 'Description must be specified', whitespace: true}]}>
                        <Input/>
                    </Form.Item>
                    <Form.Item>
                        <Button onClick={postTransaction} type="primary" htmlType="submit">Submit</Button>
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}

