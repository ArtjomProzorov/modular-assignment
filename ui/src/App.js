import React from 'react';
import './App.css';
import './components/TabBar'
import {TabBar} from "./components/TabBar";
import styled from 'styled-components';
import Divider from "antd/es/divider";

const HomePageTittle = styled.div`
font-size: 2.5em;
font-style: oblique;
color: black;
text-align: center;
`;

function App() {

    return (
        <div>
            <div style={{padding: 70}}>
                <Divider orientation="left" plain>
                    <HomePageTittle>Modular Bank Panel</HomePageTittle>
                </Divider>
                <TabBar/>
            </div>
        </div>
    );

}

export default App;