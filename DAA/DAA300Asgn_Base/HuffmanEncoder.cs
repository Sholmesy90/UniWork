using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    public class HuffmanEncoder
    {
        public String Encode(char[] charArray, Node n, Dictionary<char, DAABitArray> encodeDict)
        {
            DAABitArray bitArray = new DAABitArray();
            foreach (char c in charArray)
            {
                if (encodeDict.ContainsKey(c))
                {
                    bitArray.Append(encodeDict[c]);
                }
                else
                {
                    //Handle error.
                }
            }
         
            bitArray.Append(true);
            while (bitArray.GetCount() % 6 != 0)
            {
                bitArray.Append(false);
            }
            return ConvertBitsToText(bitArray, n, bitArray.GetCount() / 6);
        }

        private String ConvertBitsToText(DAABitArray bitArray, Node n, int numChars)
        {
            long bitRange = 0;
            String finalString = "";
            for (int i = 0; i < numChars; i++)
            {
                bitRange = bitArray.GetBitRange(0 + (i * 6), 5 + (i * 6));
                finalString += LookUpChar(bitRange);
            }
            return finalString;
        }

        private char LookUpChar(long i)
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
