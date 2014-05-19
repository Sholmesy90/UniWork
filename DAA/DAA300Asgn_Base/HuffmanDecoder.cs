using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    public class HuffmanDecoder
    {
        public String Decode(char[] charArray, Node n)
        {
            DAABitArray bitArray = ConvertTextToBits(charArray);
            return ParseTree(n, bitArray); 
        }

        public DAABitArray ConvertTextToBits(char[] charArray)
        {
            DAABitArray bitArray = new DAABitArray();
            int temp;
            String binary;
            foreach (char c in charArray)
            {
                temp = CharToDecimal(c);
                binary = Convert.ToString(temp, 2);
                foreach (char d in binary.ToCharArray())
                {
                    if (d == '0')
                        bitArray.Append(false);
                    else
                        bitArray.Append(true);
                }
            }
            return bitArray;
        }

        public String ParseTree(Node n, DAABitArray bitArray)
        {
            String finalString = "";
            Node temp = n;
            for (int i = 0; i < bitArray.GetCount(); i++)
            {
                if (!temp.IsBranch())
                {
                    finalString += temp.GetSymbol();
                    temp = n;
                }
                else
                {
                    if (bitArray.GetBitAsBool(0))
                    {
                        temp = temp.GetLeft();
                    }
                    else
                    {
                        temp = temp.GetRight();
                    }
                    bitArray.RemoveFirstBit();
                }
            }
            return finalString;
        }

        public int CharToDecimal(char c)
        {
            int number = 0;
            if ((c >= '0') && (c <= '9'))
                number = (int)c + 5;
            else if ((c >= 'a') && (c <= 'z'))
                number = (int)c - 70;
            else if ((c >= 'A') && (c <= 'Z'))
                number = (int)c - 64;
            else if (c == '\n')
                number = 63;
            else if (c == ' ')
                number = 0;
            return number;
        }

    }
}
