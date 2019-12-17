/**
  * Licensed to the Apache Software Foundation (ASF) under one or more
  * contributor license agreements.  See the NOTICE file distributed with
  * this work for additional information regarding copyright ownership.
  * The ASF licenses this file to You under the Apache License, Version 2.0
  * (the "License"); you may not use this file except in compliance with
  * the License.  You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package io.tokenanalyst.blockchainrpc.test.ethereum

import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpec
import com.softwaremill.diffx.scalatest.DiffMatcher
import io.tokenanalyst.blockchainrpc.ethereum.Protocol.{BlockResponse, _}
import io.tokenanalyst.blockchainrpc.Codecs._
import io.circe.generic.auto._
import io.circe.parser.decode

class ProtocolSpec extends AnyFlatSpec with Matchers with DiffMatcher {

  behavior of "Ethereum protocol"

  it should """decode ReceiptResponse UA to UA Pre-byzantinium""" in {
    val response =
      """
  {
      "jsonrpc": "2.0",
      "result": {
          "blockHash": "0x3a1fba5abd9d41457944e91ed097e039b7b12d3d7ba324a3f422db2277a48e28",
          "blockNumber": "0xcb3d",
          "contractAddress": null,
          "cumulativeGasUsed": "0xab4d",
          "from": null,
          "gasUsed": null,
          "logs": [
              {
                  "address": "0x5564886ca2c518d1964e5fcea4f423b41db9f561",
                  "blockHash": null,
                  "blockNumber": null,
                  "data": "0x",
                  "logIndex": null,
                  "removed": false,
                  "topics": [
                      "0xa6697e974e6a320f454390be03f74955e8978f1a6971ea6730542e37b66179bc",
                      "0x4d794669727374436f696e000000000000000000000000000000000000000000"
                  ],
                  "transactionHash": null,
                  "transactionIndex": null,
                  "transactionLogIndex": null,
                  "type": "pending"
              }
          ],
          "logsBloom": "0x00000000000000000100000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000200000008000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008000000000000000000004000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000400000000000000000010",
          "root": "0x31ebb0ae686c616ea261dd67c1b433a78a8238add284d7b9e8edf3d0e4789ad5",
          "to": null,
          "transactionHash": "0x218b632d932371478d1ae5a01620ebab1a2030f9dad6f8fba4a044ea6335a57e",
          "transactionIndex": "0x0"
      },
      "id": 1
  }
    """

    val decoded = decode[ReceiptResponse](response)
    decoded.isRight shouldEqual true
    decoded.right.get should matchTo(
      ReceiptResponse(
        "0x3a1fba5abd9d41457944e91ed097e039b7b12d3d7ba324a3f422db2277a48e28",
        "0xcb3d",
        None,
        None,
        None,
        "0xab4d",
        None,
        List(
          LogResponse(
            false,
            None,
            None,
            None,
            None,
            None,
            "0x5564886ca2c518d1964e5fcea4f423b41db9f561",
            "0x",
            List(
              "0xa6697e974e6a320f454390be03f74955e8978f1a6971ea6730542e37b66179bc",
              "0x4d794669727374436f696e000000000000000000000000000000000000000000"
            ),
            None,
            "pending"
          )
        ),
        "0x00000000000000000100000000000000000000000000020000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000200000008000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008000000000000000000004000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000400000000000000000010",
        None,
        "0x218b632d932371478d1ae5a01620ebab1a2030f9dad6f8fba4a044ea6335a57e",
        "0x0"
      )
    )
  }

  it should """decode ReceiptResponse on contract Pre-byzantinium""" in {
    val response =
      """
      {
        "id": 1,
        "jsonrpc": "2.0",
        "result": {
          "blockHash": "0x67c0303244ae4beeec329e0c66198e8db8938a94d15a366c7514626528abfc8c",
          "blockNumber": "0x6914b0",
          "contractAddress": "0x471a8bf3fd0dfbe20658a97155388cec674190bf",
          "from": "0xc931d93e97ab07fe42d923478ba2465f2",
          "to": null,
          "cumulativeGasUsed": "0x158e33",
          "gasUsed": "0xba2e6",
          "logs": [],
          "logsBloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
          "root": null,
          "transactionHash": "0x444172bef57ad978655171a8af2cfd89baa02a97fcb773067aef7794d6913374",
          "transactionIndex": "0x4"
        }
      }
      """

    val decoded = decode[ReceiptResponse](response)
    decoded.isRight shouldEqual true
    decoded.right.get should matchTo(
      ReceiptResponse(
        "0x67c0303244ae4beeec329e0c66198e8db8938a94d15a366c7514626528abfc8c",
        "0x6914b0",
        Some("0x471a8bf3fd0dfbe20658a97155388cec674190bf"),
        Some("0xc931d93e97ab07fe42d923478ba2465f2"),
        None,
        "0x158e33",
        Some("0xba2e6"),
        List(),
        "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
        None,
        "0x444172bef57ad978655171a8af2cfd89baa02a97fcb773067aef7794d6913374",
        "0x4"
      )
    )
  }

  it should "decode ReceiptResponse UA to UA Post-byzantinium" in {
    val response =
      """
      {
        "id": 1,
        "jsonrpc": "2.0",
        "result": {
          "blockHash": "0x67c0303244ae4beeec329e0c66198e8db8938a94d15a366c7514626528abfc8c",
          "blockNumber": "0x6914b0",
          "contractAddress": null,
          "from": "0xc931d93e97ab07fe42d923478ba2465f2",
          "to": "0xc931d93e97ab07fe42d923478ba2465f2",
          "cumulativeGasUsed": "0x158e33",
          "gasUsed": "0xba2e6",
          "logs": [],
          "logsBloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
          "root": null,
          "status": "0x1",
          "transactionHash": "0x444172bef57ad978655171a8af2cfd89baa02a97fcb773067aef7794d6913374",
          "transactionIndex": "0x4"
        }
      }
      """

    val decoded = decode[ReceiptResponse](response)
    decoded.isRight shouldEqual true
    decoded.right.get should matchTo(
      ReceiptResponse(
        "0x67c0303244ae4beeec329e0c66198e8db8938a94d15a366c7514626528abfc8c",
        "0x6914b0",
        None,
        Some("0xc931d93e97ab07fe42d923478ba2465f2"),
        Some("0xc931d93e97ab07fe42d923478ba2465f2"),
        "0x158e33",
        Some("0xba2e6"),
        List(),
        "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
        Some("0x1"),
        "0x444172bef57ad978655171a8af2cfd89baa02a97fcb773067aef7794d6913374",
        "0x4"
      )
    )
  }

  it should "decode ReceiptResponse on contract Post-byzantinium" in {
    val response =
      """
    {
      "id": 1,
      "jsonrpc": "2.0",
      "result": {
        "blockHash": "0x67c0303244ae4beeec329e0c66198e8db8938a94d15a366c7514626528abfc8c",
        "blockNumber": "0x6914b0",
        "contractAddress": "0x471a8bf3fd0dfbe20658a97155388cec674190bf",
        "from": "0xc931d93e97ab07fe42d923478ba2465f2",
        "to": null,
        "cumulativeGasUsed": "0x158e33",
        "gasUsed": "0xba2e6",
        "logs": [],
        "logsBloom": "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
        "root": null,
        "status": "0x1",
        "transactionHash": "0x444172bef57ad978655171a8af2cfd89baa02a97fcb773067aef7794d6913374",
        "transactionIndex": "0x4"
      }
    }
    """

    val decoded = decode[ReceiptResponse](response)
    decoded.isRight shouldEqual true
    decoded.right.get should matchTo(
      ReceiptResponse(
        "0x67c0303244ae4beeec329e0c66198e8db8938a94d15a366c7514626528abfc8c",
        "0x6914b0",
        Some("0x471a8bf3fd0dfbe20658a97155388cec674190bf"),
        Some("0xc931d93e97ab07fe42d923478ba2465f2"),
        None,
        "0x158e33",
        Some("0xba2e6"),
        List(),
        "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
        Some("0x1"),
        "0x444172bef57ad978655171a8af2cfd89baa02a97fcb773067aef7794d6913374",
        "0x4"
      )
    )
  }

  it should "decode TransactionResponse" in {
    val response =
      """
    {"jsonrpc":"2.0","result":{"blockHash":"0x1d59ff54b1eb26b013ce3cb5fc9dab3705b415a67127a003c3e61eb445bb8df2","blockNumber":"0x5daf3b","chainId":"0x1","condition":null,"creates":null,"from":"0xa7d9ddbe1f17865597fbd27ec712455208b6b76d","gas":"0xc350","gasPrice":"0x4a817c800","hash":"0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b","input":"0x68656c6c6f21","nonce":"0x15","publicKey":"0xd200bc91709a525ba0b5ce99053f239d890a78327eb038a87bcd4f8166a30b825a4122f0998946a4b7c7996b93e2d4aff8cf9a3fcdaffffae0badf90a3f9a4df","r":"0x1b5e176d927f8e9ab405058b2d2457392da3e20f328b16ddabcebc33eaac5fea","raw":"0xf871158504a817c80082c35094f02c1c8e6114b1dbe8937a39260b5b0a374432bb870f3dbb761620008668656c6c6f2125a01b5e176d927f8e9ab405058b2d2457392da3e20f328b16ddabcebc33eaac5feaa04ba69724e8f69de52f0125ad8b3c5c2cef33019bac3249e2c0a2192766d1721c","s":"0x4ba69724e8f69de52f0125ad8b3c5c2cef33019bac3249e2c0a2192766d1721c","standardV":"0x0","to":"0xf02c1c8e6114b1dbe8937a39260b5b0a374432bb","transactionIndex":"0x41","v":"0x25","value":"0xf3dbb76162000"},"id":1}
    """

    val decoded = decode[TransactionResponse](response)
    decoded.isRight shouldEqual true
    decoded.right.get should matchTo(
      TransactionResponse(
        "0x1d59ff54b1eb26b013ce3cb5fc9dab3705b415a67127a003c3e61eb445bb8df2",
        "0x5daf3b",
        Some("0x1"),
        "0xa7d9ddbe1f17865597fbd27ec712455208b6b76d",
        "0xc350",
        "0x4a817c800",
        "0x88df016429689c079f3b2f6ad39fa052532c56795b733da78a91ebe6a713944b",
        "0x68656c6c6f21",
        "0x15",
        "0xd200bc91709a525ba0b5ce99053f239d890a78327eb038a87bcd4f8166a30b825a4122f0998946a4b7c7996b93e2d4aff8cf9a3fcdaffffae0badf90a3f9a4df",
        "0x1b5e176d927f8e9ab405058b2d2457392da3e20f328b16ddabcebc33eaac5fea",
        "0xf871158504a817c80082c35094f02c1c8e6114b1dbe8937a39260b5b0a374432bb870f3dbb761620008668656c6c6f2125a01b5e176d927f8e9ab405058b2d2457392da3e20f328b16ddabcebc33eaac5feaa04ba69724e8f69de52f0125ad8b3c5c2cef33019bac3249e2c0a2192766d1721c",
        "0x4ba69724e8f69de52f0125ad8b3c5c2cef33019bac3249e2c0a2192766d1721c",
        "0x25",
        "0x0",
        Some("0xf02c1c8e6114b1dbe8937a39260b5b0a374432bb"),
        "0x41",
        "0xf3dbb76162000",
        None,
        None
      )
    )
  }

  it should "decode BlockResponse" in {
    val response =
      """
        {"jsonrpc":"2.0","result":{"author":"0x5a0b54d5dc17e0aadc383d2db43b0a0d3e029c4c","difficulty":"0x89e4c5695f464","extraData":"0x5050594520737061726b706f6f6c2d6574682d636e2d687a32","gasLimit":"0x9879a1","gasUsed":"0x0","hash":"0xd5e3eff1778c4735fb2a51c5c4e92bec32f9363ba23285d6d94e62c26b7c0884","logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000","miner":"0x5a0b54d5dc17e0aadc383d2db43b0a0d3e029c4c","mixHash":"0x1c0540069d772ce8fe02415a5988ad5b90b389a13d1a0643087ea75487007aef","nonce":"0xdf2bd7300001a5e8","number":"0x865801","parentHash":"0x65144a20fd4d1b2faa86d90d35b9cfcc87b67f48b21ad439167d80429b719258","receiptsRoot":"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421","sealFields":["0xa01c0540069d772ce8fe02415a5988ad5b90b389a13d1a0643087ea75487007aef","0x88df2bd7300001a5e8"],"sha3Uncles":"0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347","size":"0x21f","stateRoot":"0x4b909ee750967307134206db327f361b1877bffbdbd2c1100449dd4759c350c9","timestamp":"0x5db1e4c3","totalDifficulty":"0x2a7666ad70f09d1728f","transactions":[],"transactionsRoot":"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421","uncles":[]},"id":1}
        """
    val decoded = decode[BlockResponse](response)
    decoded.isRight shouldEqual true
    decoded.right.get should matchTo(
      new BlockResponse(
        "0x5a0b54d5dc17e0aadc383d2db43b0a0d3e029c4c",
        "0x89e4c5695f464",
        "0x5050594520737061726b706f6f6c2d6574682d636e2d687a32",
        "0x9879a1",
        "0x0",
        "0xd5e3eff1778c4735fb2a51c5c4e92bec32f9363ba23285d6d94e62c26b7c0884",
        "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
        "0x5a0b54d5dc17e0aadc383d2db43b0a0d3e029c4c",
        "0x1c0540069d772ce8fe02415a5988ad5b90b389a13d1a0643087ea75487007aef",
        "0xdf2bd7300001a5e8",
        "0x865801",
        "0x65144a20fd4d1b2faa86d90d35b9cfcc87b67f48b21ad439167d80429b719258",
        "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
        List(
          "0xa01c0540069d772ce8fe02415a5988ad5b90b389a13d1a0643087ea75487007aef",
          "0x88df2bd7300001a5e8"
        ),
        "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
        "0x21f",
        "0x4b909ee750967307134206db327f361b1877bffbdbd2c1100449dd4759c350c9",
        "0x5db1e4c3",
        "0x2a7666ad70f09d1728f",
        List(),
        "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
        List()
      )
    )
  }
}
