import * as React from "react";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import Divider from "@mui/material/Divider";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import HomeIcon from "@mui/icons-material/Home";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import BuildIcon from "@mui/icons-material/Build";
import ReportIcon from "@mui/icons-material/Report";
import LoyaltyIcon from "@mui/icons-material/Loyalty";
import SummarizeIcon from '@mui/icons-material/Summarize';
import PaidIcon from '@mui/icons-material/Paid';
import AccessTimeFilledIcon from '@mui/icons-material/AccessTimeFilled';
import { useNavigate } from "react-router-dom";

export default function Sidemenu({ open, toggleDrawer }) {
  const navigate = useNavigate();

  const handleClose = () => {
    toggleDrawer(false);
  };

  const handleNavigation = (path) => {
    navigate(path);
    handleClose();
  };

  const listOptions = () => (
    <Box
      role="presentation"
      onClick={handleClose}
    >
      <List>
        <ListItemButton onClick={() => handleNavigation("/")}>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Inicio" />
        </ListItemButton>

        <ListItemButton onClick={() => handleNavigation("/vehicles")}>
          <ListItemIcon>
            <DirectionsCarIcon />
          </ListItemIcon>
          <ListItemText primary="Vehículos" />
        </ListItemButton>

        <ListItemButton onClick={() => handleNavigation("/repair-list")}>
          <ListItemIcon>
            <PaidIcon />
          </ListItemIcon>
          <ListItemText primary="Precios Reparaciones" />
        </ListItemButton>

        <ListItemButton onClick={() => handleNavigation("/repairs")}>
          <ListItemIcon>
            <BuildIcon />
          </ListItemIcon>
          <ListItemText primary="Reparaciones" />
        </ListItemButton>

        <ListItemButton onClick={() => handleNavigation("/repairs/history")}>
          <ListItemIcon>
            <AccessTimeFilledIcon />
          </ListItemIcon>
          <ListItemText primary="Historial Reparaciones" />
        </ListItemButton>
      </List>

      <Divider />

      <List>
        <ListItemButton onClick={() => handleNavigation("/reports/report1")}>
          <ListItemIcon>
            <SummarizeIcon />
          </ListItemIcon>
          <ListItemText primary="Reporte 1" />
        </ListItemButton>

        <ListItemButton onClick={() => handleNavigation("reports/report2")}>
          <ListItemIcon>
            <SummarizeIcon />
          </ListItemIcon>
          <ListItemText primary="Reporte 2" />
        </ListItemButton>
      </List>
    </Box>
  );

  return (
    <Drawer
      anchor="left"
      open={open}
      onClose={handleClose}
    >
      {listOptions()}
    </Drawer>
  );
}
