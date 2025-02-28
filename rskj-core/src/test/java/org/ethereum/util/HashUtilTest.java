/*
 * This file is part of RskJ
 * Copyright (C) 2017 RSK Labs Ltd.
 * (derived from ethereumJ library, Copyright (c) 2016 <ether.camp>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.ethereum.util;

import co.rsk.core.RskAddress;
import org.bouncycastle.util.encoders.Hex;
import org.ethereum.crypto.HashUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HashUtilTest {

    @Test
    public void testMessageDigestCreation() {
        assertNotNull(HashUtil.makeMessageDigest());
    }

    @Test
    public void testMessageDigestAlg() {
        assertEquals("SHA-256", HashUtil.makeMessageDigest().getAlgorithm());
    }

    @Test
    public void testSha256_EmptyString() {
        String expected1 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        String result1 = ByteUtil.toHexString(HashUtil.sha256(new byte[0]));
        assertEquals(expected1, result1);
    }

    @Test
    public void testSha256_Test() {
        String expected2 = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String result2 = ByteUtil.toHexString(HashUtil.sha256("test".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testSha256_Multiple() {
        String expected1 = "1b4f0e9851971998e732078544c96b36c3d01cedf7caa332359d6f1d83567014";
        String result1 = ByteUtil.toHexString(HashUtil.sha256("test1".getBytes()));
        assertEquals(expected1, result1);

        String expected2 = "60303ae22b998861bce3b28f33eec1be758a213c86c93c076dbe9f558c11c752";
        String result2 = ByteUtil.toHexString(HashUtil.sha256("test2".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testSha3_EmptyString() {
        String expected1 = "c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470";
        String result1 = ByteUtil.toHexString(HashUtil.keccak256(new byte[0]));
        assertEquals(expected1, result1);
    }

    @Test
    public void testSha3_Test() {
        String expected2 = "9c22ff5f21f0b81b113e63f7db6da94fedef11b2119b4088b89664fb9a3cb658";
        String result2 = ByteUtil.toHexString(HashUtil.keccak256("test".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testSha3_Multiple() {
        String expected1 = "6d255fc3390ee6b41191da315958b7d6a1e5b17904cc7683558f98acc57977b4";
        String result1 = ByteUtil.toHexString(HashUtil.keccak256("test1".getBytes()));
        assertEquals(expected1, result1);

        String expected2 = "4da432f1ecd4c0ac028ebde3a3f78510a21d54087b161590a63080d33b702b8d";
        String result2 = ByteUtil.toHexString(HashUtil.keccak256("test2".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testRIPEMD160_EmptyString() {
        String expected1 = "9c1185a5c5e9fc54612808977ee8f548b2258d31";
        String result1 = ByteUtil.toHexString(HashUtil.ripemd160(new byte[0]));
        assertEquals(expected1, result1);
    }

    @Test
    public void testRIPEMD160_Test() {
        String expected2 = "5e52fee47e6b070565f74372468cdc699de89107";
        String result2 = ByteUtil.toHexString(HashUtil.ripemd160("test".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testRIPEMD160_Multiple() {
        String expected1 = "9295fac879006ff44812e43b83b515a06c2950aa";
        String result1 = ByteUtil.toHexString(HashUtil.ripemd160("test1".getBytes()));
        assertEquals(expected1, result1);

        String expected2 = "80b85ebf641abccdd26e327c5782353137a0a0af";
        String result2 = ByteUtil.toHexString(HashUtil.ripemd160("test2".getBytes()));
        assertEquals(expected2, result2);
    }

    @Test
    public void testCalcSaltAddr_BasicTest() {
        runTestCalSaltAddr("0x0000000000000000000000000000000000000000",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "00",
                "4D1A2e2bB4F88F0250f26Ffff098B0b30B26BF38");
    }

    @Test
    public void testCalcSaltAddr_DifferentAddress() {
        runTestCalSaltAddr("0xdeadbeef00000000000000000000000000000000",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "00",
                "B928f69Bb1D91Cd65274e3c79d8986362984fDA3");
    }

    @Test
    public void testCalcSaltAddr_DifferentSalt() {
        runTestCalSaltAddr("0xdeadbeef00000000000000000000000000000000",
                "000000000000000000000000feed000000000000000000000000000000000000",
                "00",
                "D04116cDd17beBE565EB2422F2497E06cC1C9833");
    }

    @Test
    public void testCalcSaltAddr_DifferentInitCode() {
        runTestCalSaltAddr("0x0000000000000000000000000000000000000000",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "deadbeef",
                "70f2b2914A2a4b783FaEFb75f459A580616Fcb5e");
    }

    @Test
    public void testCalcSaltAddr_CombinedPreviousTests() {
        runTestCalSaltAddr("0x00000000000000000000000000000000deadbeef",
                "00000000000000000000000000000000000000000000000000000000cafebabe",
                "deadbeef",
                "60f3f640a8508fC6a86d45DF051962668E1e8AC7");
    }

    @Test
    public void testCalcSaltAddr_LongerInitCode() {
        runTestCalSaltAddr("0x00000000000000000000000000000000deadbeef",
                "00000000000000000000000000000000000000000000000000000000cafebabe",
                "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef",
                "1d8bfDC5D46DC4f61D6b6115972536eBE6A8854C");
    }

    @Test
    public void testCalcSaltAddr_EmptyInitCode() {
        runTestCalSaltAddr("0x0000000000000000000000000000000000000000",
                "0000000000000000000000000000000000000000000000000000000000000000",
                "",
                "E33C0C7F7df4809055C3ebA6c09CFe4BaF1BD9e0");
    }

    @Test
    public void testToPrintableHash_ProducedHex() {
        byte[] bHash = "c5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470".getBytes();
        String expectedResult = ByteUtil.toHexString(bHash);

        String actualResult = HashUtil.toPrintableHash(bHash);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testToPrintableHash_ProducedHexForEmptyByteArray() {
        byte[] bHash = new byte[0];
        String expectedResult = ByteUtil.toHexString(bHash);

        String actualResult = HashUtil.toPrintableHash(bHash);

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = NullPointerException.class)
    public void testToPrintableHash_NullPointerExceptionForNull() {
        HashUtil.toPrintableHash(null);
    }

    private void runTestCalSaltAddr(String address, String saltString, String code, String expected){
        RskAddress r = new RskAddress(address);
        byte[] salt = Hex.decode(saltString);
        byte[] init_code = Hex.decode(code);

        String result = ByteUtil.toHexString(HashUtil.calcSaltAddr(r,init_code,salt));
        assertEquals(expected.toUpperCase(), result.toUpperCase());
    }
}
