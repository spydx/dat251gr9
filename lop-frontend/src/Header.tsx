import React, { Component } from 'react';
declare module 'logo.png'


export default class Header extends Component<{}> {
    


    render() {
    
        return (
            <div>
                <img src="logo.png" width="70px" height="70px" alt="" />
                <span data-testid="text-content" >Dette er logo.png</span>
            </div>
        );
    }
}