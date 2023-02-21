import * as React from 'react';
import { styled } from '@mui/material/styles';
import Grid from '@mui/material/Unstable_Grid2';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';
import FaceIcon from '@mui/icons-material/Face';


const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: '#FAF4F4',
    padding: theme.spacing(1),
    textAlign: 'center',
    // color: theme.palette.text.secondary,
    height: '100px',
    width: '500px'

}));

export default function MembersCard({members}) {
    return (


        <>
            <Box sx={{ width: '100%' }}>
                <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
                    
                {Object.keys(members).map((key, index) => {
                    return(
                        <Grid xs={6}>
                        <Item>
                            <div class="card">
                                <div class="card-horizontal" style={{
                                    display: "flex",
                                    flex: "1 1 auto"
                                }}>
                                    <div class="img" >
                                        <FaceIcon style={{fontSize:'100px'}}/>
                                        {/* <img class="" src="http://via.placeholder.com/100x100" alt="Card image cap" style={{borderRadius:'50%'}}/> */}
                                    </div>
                                    <div class="card-body" style={{width:'50%'}}>
                                        <h4 class="card-title"style={{paddingLeft:"5%"}}>Name</h4>
                                        <p class="card-text" style={{marginTop:'-5%',marginLeft:'2%'}}>{members[index].email}</p>
                                    </div>
                                </div>
                               
                            </div>
                        </Item>


                    </Grid>
                    )
                   
                })}
                    

                </Grid>
            </Box>
        </>
    );
}