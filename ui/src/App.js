import React from 'react';
import './App.css';
import './components/TabBar'
import {TabBar} from "./components/TabBar";
import styled from 'styled-components';
import Divider from "antd/es/divider";


const Title = styled.div`
  font-family:Georgia,serif;
  font-size: 40px;
  font-weight: bold;
  text-transform:uppercase;
  letter-spacing:2px; 
`;

function App() {

    return (
        <div>
            <div style={{padding: 70}}>
                <Divider orientation="left" plain>
                    <Title>Modular Bank Panel</Title>
                </Divider>
                <TabBar/>
            </div>
        </div>
    );

}

export default App;