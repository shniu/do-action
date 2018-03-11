---
title: ERC20 Token 标准例子及解读
date: 2018-02-28 7:00:00
tags:
  - ERC20
  - Ethereum
  - Blockchain
---

# ERC20 Token 标准

参见 [Ethereum Based Tokens](https://theethereum.wiki/w/index.php/Ethereum_Based_Tokens) 和 [ERC20 Wallet Support](https://theethereum.wiki/w/index.php/ERC20_Wallet_Support)

ERC20 Token 标准描述了以太坊的令牌合约必须实现的功能和事件。

## ERC20 Token 标准接口

以下是一个接口合约，声明所需的功能和事件以符合ERC20标准：

```
// ----------------------------------------------------------------------------
// ERC Token Standard #20 Interface
// https://github.com/ethereum/EIPs/blob/master/EIPS/eip-20-token-standard.md
// ----------------------------------------------------------------------------
contract ERC20Interface {
    function totalSupply() public constant returns (uint);
    function balanceOf(address tokenOwner) public constant returns (uint balance);
    function allowance(address tokenOwner, address spender) public constant returns (uint remaining);
    function transfer(address to, uint tokens) public returns (bool success);
    function approve(address spender, uint tokens) public returns (bool success);
    function transferFrom(address from, address to, uint tokens) public returns (bool success);

    event Transfer(address indexed from, address indexed to, uint tokens);
    event Approval(address indexed tokenOwner, address indexed spender, uint tokens);
}
```
绝大多数在以太坊区块链上的 token 都遵从 ERC20。[GNT Golem Network Token](https://theethereum.wiki/w/index.php/Golem_Network_Token)只是部分的遵从 ERC20，它没有实现 `approve(...)`, `allowance(..)`, `transferFrom(...)` 函数和 `Approval(...)` 事件。

token 包含的其他一些信息：

```
string public constant name = "Token Name";
string public constant symbol = "SYM";
uint8 public constant decimals = 18;  // 18 is the most common number of decimal places
```

## Token 合约如何工作

以下是 token 合约的一个片段，用于演示 token 合约如何维护以太坊帐户的 token 余额：

```
contract TokenContractFragment {

    // Balances for each account
    mapping(address => uint256) balances;

    // Owner of account approves the transfer of an amount to another account
    mapping(address => mapping (address => uint256)) allowed;

    // Get the token balance for account `tokenOwner`
    function balanceOf(address tokenOwner) public constant returns (uint balance) {
        return balances[tokenOwner];
    }

    // Transfer the balance from owner's account to another account
    function transfer(address to, uint tokens) public returns (bool success) {
        balances[msg.sender] = balances[msg.sender].sub(tokens);
        balances[to] = balances[to].add(tokens);
        Transfer(msg.sender, to, tokens);
        return true;
    }

    // Send `tokens` amount of tokens from address `from` to address `to`
    // The transferFrom method is used for a withdraw workflow, allowing contracts to send
    // tokens on your behalf, for example to "deposit" to a contract address and/or to charge
    // fees in sub-currencies; the command should fail unless the _from account has
    // deliberately authorized the sender of the message via some mechanism; we propose
    // these standardized APIs for approval:
    function transferFrom(address from, address to, uint tokens) public returns (bool success) {
        balances[from] = balances[from].sub(tokens);
        allowed[from][msg.sender] = allowed[from][msg.sender].sub(tokens);
        balances[to] = balances[to].add(tokens);
        Transfer(from, to, tokens);
        return true;
    }

    // Allow `spender` to withdraw from your account, multiple times, up to the `tokens` amount.
    // If this function is called again it overwrites the current allowance with _value.
    function approve(address spender, uint tokens) public returns (bool success) {
        allowed[msg.sender][spender] = tokens;
        Approval(msg.sender, spender, tokens);
        return true;
    }
}
```

### Token 余额

例如，假设这个合约有两个持有者

* `0x1111111111111111111111111111111111111111`有 100 单元的余额
* `0x2222222222222222222222222222222222222222`有 200 单元的余额

token 合约的 `balances` 数据结构就像下边这样：

```
balances[0x1111111111111111111111111111111111111111] = 100
balances[0x2222222222222222222222222222222222222222] = 200
```

`balanceOf(...)` 函数将会返回：

```
tokenContract.balanceOf(0x1111111111111111111111111111111111111111) will return 100
tokenContract.balanceOf(0x2222222222222222222222222222222222222222) will return 200
```

### token 转移

如果 `0x1111111111111111111111111111111111111111` 想转移 10 token 给 `0x2222222222222222222222222222222222222222`, `0x1111111111111111111111111111111111111111` 将执行下面的函数:

```
tokenContract.transfer(0x2222222222222222222222222222222222222222, 10)
```
token 合约的 `transfer(...)` 函数将会修改 `balances` 数据结构：

```
balances[0x1111111111111111111111111111111111111111] = 90
balances[0x2222222222222222222222222222222222222222] = 210
```

`balanceOf(...)` 函数将返回
```
tokenContract.balanceOf(0x1111111111111111111111111111111111111111) will return 90
tokenContract.balanceOf(0x2222222222222222222222222222222222222222) will return 210
```

### Approve And TransferFrom Token Balance

如果 `0x1111111111111111111111111111111111111111` 想授权 `0x2222222222222222222222222222222222222222` 转移一些 token 给 `0x2222222222222222222222222222`222222222222`, `0x1111111111111111111111111111111111111111` 要执行如下函数:
```
tokenContract.approve(0x2222222222222222222222222222222222222222, 30)
```
`approve` 数据结构将是这样的：

```
tokenContract.allowed[0x1111111111111111111111111111111111111111][0x2222222222222222222222222222222222222222] = 30
```

如果 `0x2222222222222222222222222222222222222222` 想以后转移一些 token 从 `0x1111111111111111111111111111111111111111` 给自己, `0x2222222222222222222222222222222222222222` 将执行 `transferFrom(...)` 函数:

```
tokenContract.transferFrom(0x1111111111111111111111111111111111111111, 0x2222222222222222222222222222222222222222, 20)
```

`balances` 数据结构将会被修改成如下的样子：

```
tokenContract.balances[0x1111111111111111111111111111111111111111] = 70
tokenContract.balances[0x2222222222222222222222222222222222222222] = 230
```

`approve` 数据结构将会是这样：

```
tokenContract.allowed[0x1111111111111111111111111111111111111111][0x2222222222222222222222222222222222222222] = 10
```

`0x2222222222222222222222222222222222222222` can still spend 10 tokens from `0x1111111111111111111111111111111111111111`.

`balanceOf(...)` 函数将返回:

```
tokenContract.balanceOf(0x1111111111111111111111111111111111111111) will return 70
tokenContract.balanceOf(0x2222222222222222222222222222222222222222) will return 230
```
## 固定供应量的 token 合约例子

下面是一个[固定供应量的 token 合约](https://github.com/bokkypoobah/Tokens#fixed-supply-token)例子，固定发行 1,000,000 单元，最初分配给合同所有者。该令牌具有18位小数位

```
pragma solidity ^0.4.18;

// ----------------------------------------------------------------------------
// 'FIXED' 'Example Fixed Supply Token' token contract
//
// Symbol      : FIXED
// Name        : Example Fixed Supply Token
// Total supply: 1,000,000.000000000000000000
// Decimals    : 18
//
// Enjoy.
//
// (c) BokkyPooBah / Bok Consulting Pty Ltd 2017. The MIT Licence.
// ----------------------------------------------------------------------------

// ----------------------------------------------------------------------------
// Safe maths
// ----------------------------------------------------------------------------
library SafeMath {
    function add(uint a, uint b) internal pure returns (uint c) {
        c = a + b;
        require(c >= a);
    }
    function sub(uint a, uint b) internal pure returns (uint c) {
        require(b <= a);
        c = a - b;
    }
    function mul(uint a, uint b) internal pure returns (uint c) {
        c = a * b;
        require(a == 0 || c / a == b);
    }
    function div(uint a, uint b) internal pure returns (uint c) {
        require(b > 0);
        c = a / b;
    }
}

// ----------------------------------------------------------------------------
// ERC Token Standard #20 Interface
// https://github.com/ethereum/EIPs/blob/master/EIPS/eip-20-token-standard.md
// ----------------------------------------------------------------------------
contract ERC20Interface {
    function totalSupply() public constant returns (uint);
    function balanceOf(address tokenOwner) public constant returns (uint balance);
    function allowance(address tokenOwner, address spender) public constant returns (uint remaining);
    function transfer(address to, uint tokens) public returns (bool success);
    function approve(address spender, uint tokens) public returns (bool success);
    function transferFrom(address from, address to, uint tokens) public returns (bool success);

    event Transfer(address indexed from, address indexed to, uint tokens);
    event Approval(address indexed tokenOwner, address indexed spender, uint tokens);
}

// ----------------------------------------------------------------------------
// Contract function to receive approval and execute function in one call
//
// Borrowed from MiniMeToken
// ----------------------------------------------------------------------------
contract ApproveAndCallFallBack {
    function receiveApproval(address from, uint256 tokens, address token, bytes data) public;
}

// ----------------------------------------------------------------------------
// Owned contract
// ----------------------------------------------------------------------------
contract Owned {
    address public owner;
    address public newOwner;

    event OwnershipTransferred(address indexed _from, address indexed _to);

    function Owned() public {
        owner = msg.sender;
    }

    modifier onlyOwner {
        require(msg.sender == owner);
        _;
    }

    function transferOwnership(address _newOwner) public onlyOwner {
        newOwner = _newOwner;
    }
    function acceptOwnership() public {
        require(msg.sender == newOwner);
        OwnershipTransferred(owner, newOwner);
        owner = newOwner;
        newOwner = address(0);
    }
}

// ----------------------------------------------------------------------------
// ERC20 Token, with the addition of symbol, name and decimals and an
// initial fixed supply
// ----------------------------------------------------------------------------
contract FixedSupplyToken is ERC20Interface, Owned {
    using SafeMath for uint;

    string public symbol;
    string public  name;
    uint8 public decimals;
    uint public _totalSupply;

    mapping(address => uint) balances;
    mapping(address => mapping(address => uint)) allowed;

    // ------------------------------------------------------------------------
    // Constructor
    // ------------------------------------------------------------------------
    function FixedSupplyToken() public {
        symbol = "FIXED";
        name = "Example Fixed Supply Token";
        decimals = 18;
        _totalSupply = 1000000 * 10**uint(decimals);
        balances[owner] = _totalSupply;
        Transfer(address(0), owner, _totalSupply);
    }


    // ------------------------------------------------------------------------
    // Total supply
    // ------------------------------------------------------------------------
    function totalSupply() public constant returns (uint) {
        return _totalSupply  - balances[address(0)];
    }


    // ------------------------------------------------------------------------
    // Get the token balance for account `tokenOwner`
    // ------------------------------------------------------------------------
    function balanceOf(address tokenOwner) public constant returns (uint balance) {
        return balances[tokenOwner];
    }

    // ------------------------------------------------------------------------
    // Transfer the balance from token owner's account to `to` account
    // - Owner's account must have sufficient balance to transfer
    // - 0 value transfers are allowed
    // ------------------------------------------------------------------------
    function transfer(address to, uint tokens) public returns (bool success) {
        balances[msg.sender] = balances[msg.sender].sub(tokens);
        balances[to] = balances[to].add(tokens);
        Transfer(msg.sender, to, tokens);
        return true;
    }


    // ------------------------------------------------------------------------
    // Token owner can approve for `spender` to transferFrom(...) `tokens`
    // from the token owner's account
    //
    // https://github.com/ethereum/EIPs/blob/master/EIPS/eip-20-token-standard.md
    // recommends that there are no checks for the approval double-spend attack
    // as this should be implemented in user interfaces
    // ------------------------------------------------------------------------
    function approve(address spender, uint tokens) public returns (bool success) {
        allowed[msg.sender][spender] = tokens;
        Approval(msg.sender, spender, tokens);
        return true;
    }


    // ------------------------------------------------------------------------
    // Transfer `tokens` from the `from` account to the `to` account
    //
    // The calling account must already have sufficient tokens approve(...)-d
    // for spending from the `from` account and
    // - From account must have sufficient balance to transfer
    // - Spender must have sufficient allowance to transfer
    // - 0 value transfers are allowed
    // ------------------------------------------------------------------------
    function transferFrom(address from, address to, uint tokens) public returns (bool success) {
        balances[from] = balances[from].sub(tokens);
        allowed[from][msg.sender] = allowed[from][msg.sender].sub(tokens);
        balances[to] = balances[to].add(tokens);
        Transfer(from, to, tokens);
        return true;
    }


    // ------------------------------------------------------------------------
    // Returns the amount of tokens approved by the owner that can be
    // transferred to the spender's account
    // ------------------------------------------------------------------------
    function allowance(address tokenOwner, address spender) public constant returns (uint remaining) {
        return allowed[tokenOwner][spender];
    }


    // ------------------------------------------------------------------------
    // Token owner can approve for `spender` to transferFrom(...) `tokens`
    // from the token owner's account. The `spender` contract function
    // `receiveApproval(...)` is then executed
    // ------------------------------------------------------------------------
    function approveAndCall(address spender, uint tokens, bytes data) public returns (bool success) {
        allowed[msg.sender][spender] = tokens;
        Approval(msg.sender, spender, tokens);
        ApproveAndCallFallBack(spender).receiveApproval(msg.sender, tokens, this, data);
        return true;
    }


    // ------------------------------------------------------------------------
    // Don't accept ETH
    // ------------------------------------------------------------------------
    function () public payable {
        revert();
    }


    // ------------------------------------------------------------------------
    // Owner can transfer out any accidentally sent ERC20 tokens
    // ------------------------------------------------------------------------
    function transferAnyERC20Token(address tokenAddress, uint tokens) public onlyOwner returns (bool success) {
        return ERC20Interface(tokenAddress).transfer(owner, tokens);
    }
}
```
```

### 其他的sample

* [How to issue your own token on Ethereum in less than 20 minutes](https://medium.com/bitfwd/how-to-issue-your-own-token-on-ethereum-in-less-than-20-minutes-ac1f8f022793)
* Ethereum.org Token page: https://www.ethereum.org/token.
* Etherscan token selection of popular tokens: https://etherscan.io/tokens
* EtherScan ERC20 token search: https://etherscan.io/token-search
* The HumanStandardToken: a specialisation of ERC20 that provides a name, decimals, symbol and version as public variables, so these can be read from the contract and do not need to be configured: https://github.com/ConsenSys/Tokens/blob/master/Token_Contracts/contracts/HumanStandardToken.sol
* Token Factory, an application that lets you create these tokens, just to play around with: https://tokenfactory.surge.sh
* Libraries:
    * OpenZeppelin: https://github.com/OpenZeppelin/zeppelin-solidity/tree/master/contracts/token
    * Minime token contract that allows to clone itself, so it can be used for things like voting, or even splitting off a token for a separate application: https://github.com/Giveth/minime

## 其他资源

* [以太坊 Token Tracker](https://etherscan.io/tokens)

目前总共有 41030 个 ERC20 的 Token 合约。



---

via: [ERC20_TOKEN_STANDARD](https://theethereum.wiki/w/index.php/ERC20_Token_Standard)

Author: [chaintech.info](https://github.com/chaintechinfo)

