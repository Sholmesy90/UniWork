using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    class HuffmanDecoder
    {
        public String Decode(char[] charArray, Node n, Dictionary<char, DAABitArray> encodeDict)
        {
            //Convert the chars to bitArray.
            ConvertTextToBits(charArray);
            DAABitArray bitArray = new DAABitArray();
            return "";
        }

        public void ConvertTextToBits(char[] charArray)
        {

        }

    }
}
