---
title: ERC20 Token 标准
date: 2018-02-27 13:00:00
tags:
  - ERC20
  - Ethereum
  - Blockchain
---


# Part2 EIP 20

## 前言

```
EIP: 20
Title: ERC-20 Token Standard
Author: Fabian Vogelsteller <fabian@ethereum.org>, Vitalik Buterin <vitalik.buterin@ethereum.org>
Type: Standard
Category: ERC
Status: Accepted
Created: 2015-11-19
```

## 简单的总结

token 的标准接口。

## 摘要

以下标准允许在智能合约中实施令牌的标准 API。该标准提供了转移 token 的基本功能，并允许 token 被批准，以便其他链上的第三方可以使用它们。

## 动机

标准接口允许其他应用程序重复使用以太坊上的任何令牌：从钱包到去中心化的交易。

## 规范

### Token

#### 方法

**注意**：调用者必须处理 `returns (bool success)` 为 `false` 的情况。调用者绝不能假设永远不会返回 `false`。

##### name

返回 token 的名称 - 例如：`MyToken`

可选 - 此方法可用于提高可用性，但接口和其他合约不能期望这些值存在。

```
function name() constant returns (string name)
```

##### symbol

返回 token 的符号，也就是代币的简称，例如：`HIX`

可选 - 此方法可用于提高可用性，但接口和其他合同不能期望这些值存在。

```
function symbol() constant returns (string symbol)
```

##### decimals

返回 token 使用的小数位数，例如 8，意味着将令牌数量除以 `100000000` 以获得其用户表示。

可选 - 此方法可用于提高可用性，但接口和其他合同不能期望这些值存在。

```
function decimals() constant returns (uint8 decimals)
```

##### totalSupply

返回供应的 token 总量，也就是发行代币的总量。所有智能合约发行的代币总量是一定的，totalSupply 必须设置初始值。

```
function totalSupply() constant returns (uint256 totalSupply)
```

##### balanceOf

返回地址为 `_owner` 帐户的帐户余额。

```
function balanceOf(address _owner) constant returns (uint256 balance)
```

##### transfer

向地址 `_to` 转移 `_value` 数量的 token，并且必须触发 `Transfer` 事件。如果 `_from` 的账户余额没有足够的 token 消费，函数必须抛出异常。

注意 0值的转移必须被视为正常转移并触发 `Transfer` 事件。

```
function transfer(address _to, uint256 _value) returns (bool success)
```

##### transferFrom

从地址 `_from` 向地址 `_to` 转移 `_value` 数量的 token，但必须触发 `Transfer` 事件。

`transferfrom` 方法用于提取工作流，允许合约代表你转移 token。例如，可以允许合约代表你转移 token 或以子货币收取费用。该函数应该抛出，除非 `_from` 帐户通过某种机制故意授权消息的发送者。

注意 0值的转移必须被视为正常转移并触发 `Transfer` 事件。

```
function transferFrom(address _from, address _to, uint256 _value) returns (bool success)
```

##### approve

允许 `_spender` 多次从你的账户里提现，直至 `_value` 的数量。如果再次调用此函数，它将以 `_value` 覆盖当前的余量。

**注意**：为了阻止向量攻击（[here](https://docs.google.com/document/d/1YLPtQxZu1UAvO9cZ1O2RPXBbT0mooh4DYKjA_jp-RLM/) and [here](https://github.com/ethereum/EIPs/issues/20#issuecomment-263524729)），客户端需要确认以这样的方式创建用户接口，即将它们设置为0，然后将其设置为同一个花费者的另一个值。虽然合同本身不应该强制执行，允许向后兼容以前部署的合同兼容性

```
function approve(address _spender, uint256 _value) returns (bool success)
```

##### allowance

返回 `_spender` 还能提取 token 的个数

```
function allowance(address _owner, address _spender) constant returns (uint256 remaining)
```

##### 对 approve transferFrom allowance 的解释

账户 A 有 1000 个 ETH，想允许 B 账户随意调用 100 个 ETH。A 账户按照以下形式调用 approve 函数 `approve(B, 100)`。当 B 账户想用这 100 个 ETH 中的 10 个 ETH 给 C 账户时，则调用 `transferFrom(A, C, 10)`。这时调用 `allowance(A, B)` 可以查看B账户还能够调用 A 账户多少个 token。


#### 事件

##### Transfer

当 token 被转移时必须被触发，0 个 token 也会触发

当创建 token 时，创建新 token 的 token 合约应该触发一个传输事件，并将 `_from` 地址设置为 `0x0`。

```
event Transfer(address indexed _from, address indexed _to, uint256 _value)
```
##### Approval

当调用approval函数成功时，一定要触发Approval事件

```
event Approval(address indexed _owner, address indexed _spender, uint256 _value)
```

#### 实现

以太坊网络上已经有大量符合 ERC20 的令牌。 不同的实施由不同的团队编写，这些团队有不同的权衡：从节约燃料到提高安全性。

##### 实例

* https://github.com/OpenZeppelin/zeppelin-solidity/blob/master/contracts/token/ERC20/StandardToken.sol
* https://github.com/ConsenSys/Tokens/blob/master/contracts/eip20/EIP20.sol

##### 再次调用 approve 之前向0添加强制的实现

* https://github.com/Giveth/minime/blob/master/contracts/MiniMeToken.sol

#### 历史资源

* [V神写的最原始的协议](https://github.com/ethereum/wiki/wiki/Standardized_Contract_APIs/499c882f3ec123537fc2fccd57eaa29e6032fe4a)
* [Reddit 上的讨论](https://www.reddit.com/r/ethereum/comments/3n8fkn/lets_talk_about_the_coin_standard/)
* [最开始的 issue #20](https://github.com/ethereum/EIPs/issues/20)

---

via: https://github.com/ethereum/EIPs/blob/master/EIPS/eip-20.md


