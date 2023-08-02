pragma solidity ^0.4.25;

contract HelloWorld{
    string str;
    
    constructor(){
        str = "Hello World";
    }
    
    function setStr(string memory _str)public {
        str = _str;
    }
    
    function getStr()public view returns(string memory){
        return str;
    }
}