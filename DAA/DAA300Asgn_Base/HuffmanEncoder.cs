using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    /// This class is responsible for physically encoding the message
    /// and returning a string to display to the user. It has one
    /// public method, which is Encode, which will return the String
    /// value of the encoded message.
    public class HuffmanEncoder
    {
        /// Takes the char array and the dictionary to generate a bitset
        /// which is then used to create an encoded character string.
        public String Encode(char[] charArray, Dictionary<char, DAABitArray> encodeDict)
        {
            DAABitArray bitArray = new DAABitArray();
            foreach (char c in charArray)
            {
                if (encodeDict.ContainsKey(c))
                {
                    bitArray.Append(encodeDict[c]);
                }
            }

            /// We append a '1' on the end of the bit set, followed by a series
            /// of '0's to pad the bitset to 6 bits. This means when we get to
            /// decompressing this bitset, we can remove all the 0's off the 
            /// end until a  '1' is reached, which is then removed to give 
            /// the actual encoded bitset.
            bitArray.Append(true); 
            while (bitArray.GetCount() % 6 != 0)
            {
                bitArray.Append(false);
            }
            return ConvertBitsToText(bitArray, bitArray.GetCount() / 6);
        }

        /// Converts a bit array, into a character representation of that bitset.
        /// Bitsets are defined to be 6 bits long or 2^6, making a possible
        /// 64 characters represented.
        private String ConvertBitsToText(DAABitArray bitArray, int numChars)
        {
            long bitRange = 0;
            String finalString = "";
            for (int i = 0; i < numChars; i++)
            {
                bitRange = bitArray.GetBitRange(0 + (i * 6), 5 + (i * 6));
                finalString += DecimalToChar(bitRange);
            }
            return finalString;
        }

        /// Table for retrieving the char value of the bitset, as per 
        /// assignment specification.
        private char DecimalToChar(long i)
        {
            char c = '!';
            if (i == 0)
                c = ' ';
            else if ((i >= 1) && (i <= 26))
                c = (char)(i + 64);
            else if ((i >= 27) && (i <= 52))
                c = (char)(i + 70);
            else if ((i >= 53) && (i <= 62))
                c = (char)(i - 5);
            else if (i == 63)
                c = '\n';
            return c;
        }
    }
}
