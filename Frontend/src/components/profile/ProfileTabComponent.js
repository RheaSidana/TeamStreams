
import React, { memo, useContext, useEffect, useState } from "react";
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Unstable_Grid2';
import Paper from '@mui/material/Paper';
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
// import MembersCard from "./MembersCard"
import VideoCard from "../videoCard/VideoCard";
import {default as VideoStyles} from "../videoCard/styles/VideoCardStyles.module.css"


const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: '#FAF4F4',
    padding: theme.spacing(1),
    textAlign: 'center',
    // color: theme.palette.text.secondary,


}));

export default function ProfileTabComponent({ type, content }) {
    const [value, setValue] = React.useState('1');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    
    return (
        <Box sx={{ width: '100%', typography: 'body1' }} className={VideoStyles}>
            <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }} >
                    <TabList onChange={handleChange} aria-label="lab API tabs example">
                        {Object.keys(content).map((key, index)=>{
                            return (
                                <Tab label={key} value={index} /> 
                            )
                        })}
                        {/* <Tab label="Resources" value="4" /> */}
                    </TabList>
                </Box>
                {Object.keys(content).map((key, index)=>{
                            return (
                                <TabPanel value={index} sx={{ alignItems: 'center' }}>
                                    {content[key]}
                                    </TabPanel>
                            )
                        })}
                {/* <TabPanel value="1" sx={{ alignItems: 'center' }}>
                    For optimal user experience, Material Design interfaces need to be able to adapt their layout at various breakpoints. MUI uses a simplified implementation of the original specification.

                    The breakpoints are used internally in various components to make them responsive, but you can also take advantage of them for controlling the layout of your application through the Grid component.
                </TabPanel>
                <TabPanel value="2"  >

                </TabPanel>
                <TabPanel value="3">

                </TabPanel> */}
                {/* to implement resources */}
                {/* <TabPanel value="4">

                </TabPanel> */}
            </TabContext>
        </Box>
    );
}