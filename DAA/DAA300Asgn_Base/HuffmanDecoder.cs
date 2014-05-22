using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    /// This class is responsible for decoding an encrypted message and
    /// returning it as a string. The class has one public method Decode(),
    /// which takes a chararray (the encrypted msg) and the root node of the 
    /// Huffman Tree. 
    public class HuffmanDecoder
    {
        /// Takes the char array & node. Returns the original message.
        public String Decode(char[] charArray, Node n)
        {
            DAABitArray bitArray = ConvertTextToBits(charArray);
            bitArray = RemoveBuffer(bitArray);
            
            return ParseTree(n, bitArray); 
        }

        /// Converts the scrambled alphanumeric values provided into a bitset
        /// which will be used to parse through the Huffman Tree.
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

                /// Append leading 0's, removed from the ToString() function
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

        /// When the message is encoded, a '1' is added to the bitset,
        /// followed by a series of '0's to buffer the bitset into sections
        /// of 6 bits each (% 6). This function removes this buffer to 
        /// provide the original bitset.
        private DAABitArray RemoveBuffer(DAABitArray bitArray)
        {
            while (!bitArray.GetBitAsBool(bitArray.GetCount() -1))
            {
                bitArray.RemoveLastBit(); /// Remove '0's
            }
            bitArray.RemoveLastBit(); /// Remove '1'
            return bitArray;
        }

        /// Parses the Huffman tree to retrieve symbol values of the
        /// provided bitset. 
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

        /// Convert a char into the decimal value as per the assignment
        /// specification.
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
