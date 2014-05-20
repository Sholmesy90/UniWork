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
            Console.WriteLine(bitArray);
            bitArray = RemoveBuffer(bitArray);
            
            return ParseTree(n, bitArray); 
        }

        private DAABitArray ConvertTextToBits(char[] charArray)
        {
            DAABitArray bitArray = new DAABitArray();
            int temp;
            String binary = "";
            String bitString = "";
            foreach (char c in charArray)
            {
                temp = CharToDecimal(c);
                bitString = Convert.ToString(temp, 2);
                while (bitString.Length < 6)
                {
                    bitString = "0" + bitString;
                }
                binary += bitString;
            }

            foreach (char d in binary.ToCharArray())
            {
                if (d == '0')
                    bitArray.Append(false);
                else
                    bitArray.Append(true);
            }
            return bitArray;
        }

        private DAABitArray RemoveBuffer(DAABitArray bitArray)
        {
            while (!bitArray.GetBitAsBool(bitArray.GetCount() -1))
            {
                bitArray.RemoveLastBit();
            }
            bitArray.RemoveLastBit();
            return bitArray;
        }

        private String ParseTree(Node n, DAABitArray bitArray)
        {
            String finalString = "";
            Node temp = n;
            while (bitArray.GetCount() > 0)
            {
                while (temp.IsBranch())
                {
                    if (bitArray.GetBitAsBool(0))
                    {
                        temp = temp.GetRight();
                        bitArray.RemoveFirstBit();
                    }
                    else
                    {
                        temp = temp.GetLeft();
                        bitArray.RemoveFirstBit();
                    }
                }
                finalString += temp.GetSymbol();
                temp = n;
            }
            return finalString;
        }

        private int CharToDecimal(char c)
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
