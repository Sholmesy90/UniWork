﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Asgn
{
    /// This class is responsible for generating the Huffman Tree and 
    /// also creating a dictionary for the encoder to use to encode its msg.
    /// The dictionary is passed to the encoder through the method call
    /// BuildEncodingMap().
    public class HuffmanGenerator
    {
        List<Node> list;
        Dictionary<char, DAABitArray> encodeDict;

        /// Default constructor
        public HuffmanGenerator()
        {
            list = new List<Node>();
        }
        /// Alternate constructor
        public HuffmanGenerator(List<Node> inList)
        {
            list = inList;
        }

        /// Creates a Huffman Tree based on its current list value.
        /// returns the Node of the root of the tree, allowing the tree 
        /// to be used for compression and decompression.
        public Node CreateTree()
        {
            while (list.Count > 1)
            {
                list = list.OrderBy(n => n.GetFreq()).ToList();

                Node n1 = list.First();
                list.RemoveAt(0);
                Node n2 = list.First();
                list.RemoveAt(0);

                Node newNode = new Node();
                newNode.SetFreq(n1.GetFreq() + n2.GetFreq());
                newNode.SetLeft(n1);
                newNode.SetRight(n2);

                list.Add(newNode);
            }
            return list.First();
        }

        /// This function is used to build the dictionary used by compression
        /// and decompression in the algorithm. it takes the root node and 
        /// creates a stack, which it passes to the recursive function 
        /// BuildRecursive(). 
        public Dictionary<char, DAABitArray> BuildEncodingMap(Node n)
        {
            encodeDict = new Dictionary<char, DAABitArray>();
            Stack<bool> stack = new Stack<bool>();
            BuildRecursive(n, stack);
            return encodeDict;
        }

        /// This function recursively traverses the tree finding the binary 
        /// values to represent the characters of the leaf Nodes.
        private void BuildRecursive(Node n, Stack<bool> stack)
        {
            if (n.IsBranch())
            {
                stack.Push(false);
                BuildRecursive(n.GetLeft(), stack);
                stack.Pop();

                stack.Push(true);
                BuildRecursive(n.GetRight(), stack);
                stack.Pop();
            }
            else
            {
                DAABitArray bitArray = new DAABitArray();
                foreach (bool b in stack)
                {
                    if (b)
                        bitArray.Append(true);
                    else
                        bitArray.Append(false);
                }
                bitArray.Reverse(); /// Stack puts it in reverse order. Fixes.
                encodeDict.Add(n.GetSymbol(), bitArray);
            }
        }
    }
}
