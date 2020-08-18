import React, {useState} from "react";
import Form from "antd/es/form";
import Button from "antd/es/button";
import Input from "antd/es/input";
import {createAccount} from "../../service/fetchService";
import {errorWithMessage, success} from "../../service/notification";
import {Select} from 'antd';
import {countriesList} from "../PreparedCountriesList";

const formItemLayout = {
    labelCol: {
        xs: {span: 24},
        sm: {span: 4},
    },
    wrapperCol: {
        xs: {span: 24},
        sm: {span: 22},
    },
};


export default function CreateAccount() {

    const [selectedCurrencies, setSelectedCurrencies] = useState([]);
    const [country, setCountry] = useState('');
    const [customerId, setCustomerId] = useState('');
    const [form] = Form.useForm();

    const clearState = () => {
        setSelectedCurrencies([]);
        setCountry('');
        setCustomerId('');
        form.resetFields()
    };

    const currencies = ['EUR', 'GBP', 'SEK', 'USD'];
    const filteredOptions = currencies.filter(o => !selectedCurrencies.includes(o));

    const createAcc = () => {
        if (country && customerId !== '' && selectedCurrencies.length > 0) {
            const bundle = {customerId: customerId, country: country, currencies: selectedCurrencies};
            createAccount(bundle).then(() => {
                clearState();
                success('Account successfully created');
            }).catch(() => errorWithMessage('Account with provided customer id exist'));
        }
    };


    return (
        <div>
            <Form form={form}
                  {...formItemLayout}
                  style={{width: '50%'}}
                  size={"large"}>
                <Form.Item
                    label="Customer Id"
                    name="customerId"
                    rules={[{required: true, message: 'Please enter customer id'},
                        () => ({
                            validator(rule, value) {
                                if (value <= 0) {
                                    return Promise.reject('Customer id must be a positive number');
                                }
                                if (!RegExp("^(\\d+$)").test(value)) {
                                    return Promise.reject('Customer id must be a number');
                                }
                            }
                        })
                    ]}
                >
                    <Input onChange={e => setCustomerId(e.target.value)}/>
                </Form.Item>
                <Form.Item onChange={e => setCountry(e.target.value)}
                           label="Country"
                           name="country"
                           rules={[{required: true, message: 'Please input your country'}]}>
                    <Select showSearch
                            style={{width: '100%'}}
                            onChange={e => setCountry(e)}>
                        {countriesList.map(c => <Select.Option value={c}>{c}</Select.Option>)}
                    </Select>
                </Form.Item>
                <Form.Item label="Currencies" name="Currencies"
                           rules={[{required: true, message: 'Please select currency'}]}>
                    <Select notFoundContent={
                        <div>CURRENCY NOT SUPPORTED JUST YET</div>
                    }
                            mode="multiple"
                            placeholder="Please choose currency"
                            value={selectedCurrencies}
                            onChange={setSelectedCurrencies}
                    >
                        {filteredOptions.map(item => (
                            <Select.Option key={item} value={item}>
                                {item}
                            </Select.Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item>
                    <Button onClick={createAcc} type="primary" htmlType="submit">Create Account</Button>
                </Form.Item>
            </Form>
        </div>
    )

}